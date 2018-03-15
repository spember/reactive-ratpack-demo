package demo.reactiveratpack.modules.rabbitmq

import com.google.inject.Inject
import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import com.thirdchannel.rabbitmq.activation.Activations
import demo.reactiveratpack.modules.websocket.WebSocketProcessorService
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import ratpack.service.Service
import ratpack.service.StartEvent

@Slf4j
@CompileStatic
class RabbitMqModule implements Service {
    private final static String EXCHANGE = "demo"
    private final static String INCOMING_QUEUE = "demo_queue"
    private final static String INCOMING_CONSUMER_TAG ="demo_tag"



    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Channel outgoingMessageChannel;
    private Channel incomingMessageChannel;
    private Channel authenticationChannel;
    private Activations activations;

    private static final AMQP.BasicProperties PROPERTIES =
            new AMQP.BasicProperties.Builder()
                    .appId("data-import-aggregation")
                    .contentEncoding("UTF-8")
                    .contentType("application/json")
                    .build();


    private WebSocketProcessorService webSocketProcessorService

    @Inject
    RabbitMqModule(final WebSocketProcessorService webSocketProcessorService) {
        this.webSocketProcessorService = webSocketProcessorService
    }

    @Override
    void onStart(final StartEvent event) throws Exception {
        //connect to rabbit
        connection = connectionFactory.newConnection();

        incomingMessageChannel = connection.createChannel();
        outgoingMessageChannel = connection.createChannel();
        authenticationChannel = connection.createChannel();

        activations = new Activations.Builder()
                .withExceptionHandler({e-> log.error("Error in activation", e)})
                .withIncomingMappings({c ->
            c
//                    .add(DATA_FILE_UPLOADED, DataFileUploaded.class)
//                    .add(PROGRAM_UPDATED, ProgramUpdatedIncomingEvent.class)
//                    .add(CUSTOMER_UPDATED, CustomerUpdatedIncomingEvent.class)
        })
                .withOutgoingMappings({c ->
            c
                    //.add(DataFileUploaded.class, EXCHANGE, DATA_FILE_UPLOADED, e -> PROPERTIES)
        })
                .byType({c ->
            c
//                    .activateAndPublish(DataFileUploaded.class, event -> dataImportService.event(event.getObject()))
//                    .activate(ProgramUpdatedIncomingEvent.class, event -> programEventReceiverService.handle(event.getObject()))
//                    .activate(CustomerUpdatedIncomingEvent.class, event -> programEventReceiverService.handle(event.getObject()))
        })
                .withOutgoingChannel(outgoingMessageChannel)
                .withIncomingQueue(incomingMessageChannel, EXCHANGE, INCOMING_QUEUE, INCOMING_CONSUMER_TAG)
                .connect();
    }

    private ConnectionFactory buildConnectionFactory() {
        ConnectionFactory rabbitMqConnectionFactory = new ConnectionFactory();
        // hard coded string s are no bueno, but it's fine for this demo
        rabbitMqConnectionFactory.setUri("amqp://rabbit_user_dev:password@127.0.0.1:5672/%2Fdemo");

        rabbitMqConnectionFactory.setTopologyRecoveryEnabled(true);
        rabbitMqConnectionFactory.setAutomaticRecoveryEnabled(true);


        return rabbitMqConnectionFactory;
    }
}
