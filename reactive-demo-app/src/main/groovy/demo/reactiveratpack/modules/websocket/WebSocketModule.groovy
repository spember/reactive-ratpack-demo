package demo.reactiveratpack.modules.websocket

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Scopes
import com.google.inject.Singleton
import groovy.transform.CompileStatic

@CompileStatic
class WebSocketModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(WebsocketApiMappings.class).in(Scopes.SINGLETON)
        bind(WebSocketProcessorService.class).in(Scopes.SINGLETON)
    }

    @Provides
    @Singleton
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper()
        mapper.registerModule(new JavaTimeModule())
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true)
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        mapper
    }

}
