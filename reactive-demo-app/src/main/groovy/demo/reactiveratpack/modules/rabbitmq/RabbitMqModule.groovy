package demo.reactiveratpack.modules.rabbitmq

import com.google.inject.AbstractModule
import com.google.inject.Scopes
import groovy.transform.CompileStatic

@CompileStatic
class RabbitMqModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(RabbitInitService).in(Scopes.SINGLETON)
    }
}
