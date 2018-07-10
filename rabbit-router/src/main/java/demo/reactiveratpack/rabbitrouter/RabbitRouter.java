package demo.reactiveratpack.rabbitrouter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.function.Consumer;

/**
 *
 */
public class RabbitRouter {

    /*
        need to use a builder to create a Supplier of error messages, a mapping of topics to deserializers, and then
        mapping of classes to handlers
     */

    /*
        Accept an event, attempt a deserialization based on topic. then pass to handler based on class
     */

    //private final static String INTERNAL_CONSUMER_TAG = "rr-internal-consumer";

    private final Channel incomingChannel;
            //incomingMessageChannel, EXCHANGE, INCOMING_QUEUE, INCOMING_CONSUMER_TAG
    // todo turn these into Wrapped Classes?
    private final String exchange;
    private final String incomingQueue;
    private final String incomingConsumerTag;

    private final HashMap<String, Class<Object>> incomingMappings;
    private final HashMap<Class, Consumer<Object>> typeHandlers;

    private final ObjectMapper objectMapper;


    RabbitRouter(Builder builder) {
        this.incomingChannel = builder.inc;
        this.exchange = builder.ex;
        this.incomingQueue = builder.iQ;
        this.incomingConsumerTag = builder.icT;

        incomingMappings = new LinkedHashMap<>();
        builder.incomingMappings.forEach(incomingMappings::put);

        typeHandlers = new LinkedHashMap<>();
        builder.handlers.forEach(typeHandlers::put);

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static Builder builder() {
        return new Builder();
    }


    public static class Builder {
        Channel inc;
        String ex;
        String iQ;
        String icT;

        HashMap<String, Class> incomingMappings = new LinkedHashMap<>();
        HashMap<Class, Consumer<Object>> handlers = new LinkedHashMap<>();

        public Builder withIncomingChannel(final Channel incomingChannel) {
            this.inc = incomingChannel;
            return this;
        }

        public Builder withExchange(final String exchange) {
            this.ex = exchange;
            return this;
        }

        public Builder withIncomingQueue(final String queue) {
            this.iQ = queue;
            return this;
        }

        public Builder withIncomingConsumerTag(final String tag) {
            this.icT = tag;
            return this;
        }

        public Builder addIncomingMapping(final String routingKey, final Class clzz) {
            this.incomingMappings.put(routingKey, clzz);
            return this;
        }

        public Builder addHandler(final Class clazz, final Consumer<Object> consumer) {
            this.handlers.put(clazz, consumer);
            return this;
        }

        public RabbitRouter build() {
            return new RabbitRouter(this);
        }

    }


    public void connect() throws IOException {
        // create queue
        incomingChannel.queueDeclare(this.incomingQueue, true, false, false, null);
//        channel.queueDeclare(queueName, true, false, false, null);
        // for each incomming mapping
        for (String routingKey : incomingMappings.keySet()) {
            incomingChannel.queueBind(incomingQueue, exchange, routingKey);
        }

//        channel.queueBind(queueName, exchangeName, routingKey);

        // basic consume

        incomingChannel.basicConsume(incomingQueue, true, incomingConsumerTag, new com.rabbitmq.client.Consumer() {
            @Override
            public void handleConsumeOk(String consumerTag) {

            }

            @Override
            public void handleCancelOk(String consumerTag) {

            }

            @Override
            public void handleCancel(String consumerTag) throws IOException {

            }

            @Override
            public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {

            }

            @Override
            public void handleRecoverOk(String consumerTag) {

            }

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // attempt to deserialize based on
                if (incomingMappings.containsKey(envelope.getRoutingKey())) {
                    Class<Object> clazz = incomingMappings.get(envelope.getRoutingKey());

                    passObject(objectMapper.readValue(body, clazz));
                } else {
                    //oops
                    System.out.print("No match");
                }

            }

            private void passObject(Object message) {
                for (Class clazz : typeHandlers.keySet()) {
                    if (clazz.getName().equals(message.getClass().getName())) {
                        typeHandlers.get(clazz).accept(message);
                    }
                }
            }
        });
    }

    public void disconnect() throws IOException {
        for (String routingKey : incomingMappings.keySet()) {
            incomingChannel.queueUnbind(incomingQueue, exchange, routingKey);
        }
        incomingChannel.queuePurge(this.incomingQueue);
        incomingChannel.queueDelete(this.incomingQueue);
    }

}
