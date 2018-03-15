package demo.reactiveratpack.modules.rabbitmq

import com.google.inject.AbstractModule
import com.google.inject.Scopes
import groovy.transform.CompileStatic

@CompileStatic
class RabbitMqInitService extends AbstractModule {
    @Override
    protected void configure() {
        bind(RabbitMqInitService).in(Scopes.SINGLETON)
    }
}
