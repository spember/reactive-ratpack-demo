package demo.reactiveratpack.modules.rabbitmq

import com.google.inject.Inject
import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import demo.reactiveratpack.domain.Event
import demo.reactiveratpack.modules.websocket.WebSocketProcessorService
import demo.reactiveratpack.rabbitrouter.RabbitRouter
import demo.reactiveratpack.todo.EventReceiverService
import demo.reactiveratpack.todo.events.ListCreatedEvent
import demo.reactiveratpack.todo.events.ListNameUpdatedEvent
import groovy.util.logging.Slf4j
import io.reactivex.Flowable
import io.reactivex.functions.Function
import ratpack.service.Service
import ratpack.service.StartEvent
import org.reactivestreams.Publisher


@Slf4j
class RabbitInitService implements Service {
    private final static String EXCHANGE = "amq.topic"
    private final static String INCOMING_QUEUE = "demo_queue"
    private final static String INCOMING_CONSUMER_TAG ="demo_tag"



    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Channel outgoingMessageChannel;
    private Channel incomingMessageChannel;
    private RabbitRouter rabbitRouter

    private static final AMQP.BasicProperties PROPERTIES =
            new AMQP.BasicProperties.Builder()
                    .appId("data-import-aggregation")
                    .contentEncoding("UTF-8")
                    .contentType("application/json")
                    .build();


    private WebSocketProcessorService webSocketProcessorService
    private EventReceiverService eventReceiverService

    @Inject
    RabbitInitService(final WebSocketProcessorService webSocketProcessorService,
                      EventReceiverService eventReceiverService) {
        this.webSocketProcessorService = webSocketProcessorService
        this.eventReceiverService = eventReceiverService
        this.connectionFactory = buildConnectionFactory()
    }

    @Override
    void onStart(final StartEvent event) throws Exception {
        //connect to rabbit
        connection = connectionFactory.newConnection();

        incomingMessageChannel = connection.createChannel();
        outgoingMessageChannel = connection.createChannel();

        rabbitRouter = new RabbitRouter.Builder()
        .withExchange(EXCHANGE)
        .withIncomingChannel(incomingMessageChannel)
        .withIncomingQueue(INCOMING_QUEUE)
        .withIncomingConsumerTag(INCOMING_CONSUMER_TAG)
        .addIncomingMapping("list.created", ListCreatedEvent)
        .addIncomingMapping("list.name.updated", ListNameUpdatedEvent)
        .addHandler(ListCreatedEvent, {o ->
            processEvents(eventReceiverService.receive((ListCreatedEvent)o))
        })
        .addHandler(ListNameUpdatedEvent, {o ->
            processEvents(eventReceiverService.receive((ListNameUpdatedEvent) o))
        })
        .build().connect()

    }


    private void processEvents(Publisher<Event> eventPublisher) {
        Flowable.fromPublisher(eventPublisher)
        .map({Event outgoing -> webSocketProcessorService.transmit(outgoing)} as Function)
        .subscribe({Event nextEvent ->  }, {Throwable t ->}, {log.info("Finished receiving")})

    }


    private ConnectionFactory buildConnectionFactory() {
        ConnectionFactory rabbitMqConnectionFactory = new ConnectionFactory();
        // hard coded string s are no bueno, but it's fine for this demo
        rabbitMqConnectionFactory.setUri("amqp://guest:guest@127.0.0.1:5672/%2Ftest");

        rabbitMqConnectionFactory.setTopologyRecoveryEnabled(true);
        rabbitMqConnectionFactory.setAutomaticRecoveryEnabled(true);

        return rabbitMqConnectionFactory;
    }
}
