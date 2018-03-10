package demo.reactiveratpack.modules.websocket

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import com.google.inject.Inject
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.reactivex.processors.PublishProcessor


/**
 * Maintains a FlowableProcessor for emitting messages to a WebSocket
 */
@Slf4j
@CompileStatic
class WebSocketProcessorService {
    private PublishProcessor<String> processor
    private ObjectWriter objectWriter

    @Inject WebSocketProcessorService(ObjectMapper objectMapper) {
        processor = PublishProcessor.create()
        objectWriter = objectMapper.writer()
    }

    PublishProcessor<String> getProcessor() {
        return processor
    }

    Object transmit(Object event) {
        log.debug("Attempting to map ${event}")
        try {
            processor.onNext(objectWriter.writeValueAsString(event))
        } catch(JsonProcessingException e) {
            log.error("Could not write: ", e)
        }
        event
    }
}
