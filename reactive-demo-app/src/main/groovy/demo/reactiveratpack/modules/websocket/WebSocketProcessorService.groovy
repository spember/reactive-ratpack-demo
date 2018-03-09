package demo.reactiveratpack.modules.websocket

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import com.fasterxml.jackson.databind.SerializationConfig
import com.fasterxml.jackson.databind.SerializationFeature
import groovy.transform.CompileStatic
import io.reactivex.processors.PublishProcessor


/**
 * Maintains a FlowableProcessor for emitting messages to a WebSocket
 */
@CompileStatic
class WebSocketProcessorService {
    private PublishProcessor<String> processor
    private ObjectWriter objectWriter

    WebSocketProcessorService() {
        processor = PublishProcessor.create()

        ObjectMapper mapper = new ObjectMapper()
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true)
        objectWriter = mapper.writer()
    }

    PublishProcessor<String> getProcessor() {
        return processor
    }

    void transmit(Object event) {
        processor.onNext(objectWriter.writeValueAsString(event))
    }
}
