package demo.reactiveratpack.modules.websocket

import com.google.inject.AbstractModule
import com.google.inject.Scopes
import groovy.transform.CompileStatic

@CompileStatic
class WebSocketModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(WebsocketApiMappings.class).in(Scopes.SINGLETON)
        bind(WebSocketProcessorService.class).in(Scopes.SINGLETON)
    }

}
