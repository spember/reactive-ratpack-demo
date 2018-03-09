package demo.reactiveratpack.modules.todo

import com.google.inject.Inject
import demo.reactiveratpack.modules.websocket.WebSocketProcessorService
import demo.reactiveratpack.todo.TodoList
import demo.reactiveratpack.todo.TodoListRepository
import demo.reactiveratpack.todo.commands.CreateNewListCommand
import groovy.json.JsonOutput
import groovy.json.JsonParser
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.processors.FlowableProcessor
import ratpack.groovy.handling.GroovyChainAction

import static ratpack.jackson.Jackson.json
import static ratpack.jackson.Jackson.toJson
import static ratpack.rx2.RxRatpack.flow
import ratpack.jackson.JsonRender

@Slf4j
@CompileStatic
class TodoApiUrlMappings extends GroovyChainAction {
    private TodoListRepository todoListRepository
    private WebSocketProcessorService webSocketProcessorService

    @Inject
    TodoApiUrlMappings(final TodoListRepository todoListRepository, final WebSocketProcessorService webSocketProcessorService) {
        this.todoListRepository = todoListRepository
        this.webSocketProcessorService = webSocketProcessorService
    }

    @Override
    void execute() throws Exception {
        prefix("lists", {chain ->
            path() {
                byMethod {
                    get {
                        Flowable.fromPublisher(todoListRepository.list())
                        .toList()
                        .subscribe({List<TodoList> items ->
                            context.render(json(items))
                        }, {log.error("oops")})
                    }
                    post {
                        // create
                        flow(parse(CreateNewListCommand), BackpressureStrategy.ERROR)
                        .subscribe({command ->
                            webSocketProcessorService.transmit(command)
                            render(json([:]))
                        }, {
                            response.status(400)
                            render(json([:]))
                        })
                    }
                }
            }

        })
    }
}
