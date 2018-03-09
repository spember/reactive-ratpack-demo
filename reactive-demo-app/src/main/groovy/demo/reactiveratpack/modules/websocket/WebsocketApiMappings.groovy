package demo.reactiveratpack.modules.websocket

import com.google.inject.Inject
import groovy.transform.CompileStatic
import io.reactivex.Flowable
import io.reactivex.processors.FlowableProcessor
import ratpack.groovy.handling.GroovyChainAction
import ratpack.handling.Chain
import ratpack.handling.Context
import ratpack.rx2.internal.DefaultSchedulers
import ratpack.websocket.WebSockets

@CompileStatic
class WebsocketApiMappings extends GroovyChainAction{

    private Flowable processor

    @Inject WebsocketApiMappings(final WebSocketProcessorService webSocketProcessorService) {
        this.processor = webSocketProcessorService
            .getProcessor()
            .observeOn(DefaultSchedulers.computationScheduler)
            .subscribeOn(DefaultSchedulers.computationScheduler)
            .map({event ->
                println "Broadcasting ${event}"
                event
            })


    }

    @Override
    void execute() throws Exception {
        prefix("", {Chain chain->
            chain.get({Context context ->
                WebSockets.websocketBroadcast(context, processor)
            })
        })
    }
}
