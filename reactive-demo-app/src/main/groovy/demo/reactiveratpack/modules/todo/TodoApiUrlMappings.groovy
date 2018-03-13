package demo.reactiveratpack.modules.todo

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.inject.Inject
import demo.reactiveratpack.domain.Event
import demo.reactiveratpack.modules.websocket.WebSocketProcessorService
import demo.reactiveratpack.todo.ClientManagementService
import demo.reactiveratpack.todo.TodoList
import demo.reactiveratpack.todo.TodoListRepository
import demo.reactiveratpack.todo.commands.CreateNewItemCommand
import demo.reactiveratpack.todo.commands.CreateNewListCommand
import demo.reactiveratpack.todo.commands.UpdateItemCommand
import demo.reactiveratpack.todo.commands.UpdateListCommand
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import ratpack.groovy.handling.GroovyChainAction

import static ratpack.jackson.Jackson.json
import static ratpack.rx2.RxRatpack.flow

@Slf4j
@CompileStatic
class TodoApiUrlMappings extends GroovyChainAction {
    private TodoListRepository todoListRepository
    private WebSocketProcessorService webSocketProcessorService
    private ClientManagementService clientManagementService
    private ObjectMapper objectMapper

    @Inject
    TodoApiUrlMappings(
            final TodoListRepository todoListRepository,
            final WebSocketProcessorService webSocketProcessorService,
            final ClientManagementService clientManagementService,
            final ObjectMapper objectMapper) {
        this.todoListRepository = todoListRepository
        this.webSocketProcessorService = webSocketProcessorService
        this.clientManagementService = clientManagementService
        this.objectMapper = objectMapper
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
                            context.render(json(items, objectMapper.writer()))
                        }, {log.error("oops")})
                    }
                    post {
                        // create
                        flow(parse(CreateNewListCommand), BackpressureStrategy.ERROR)
                        .flatMap({CreateNewListCommand command -> clientManagementService.handle(command)})
                        .map({webSocketProcessorService.transmit(it)})
                        .toList()
                        .subscribe({List<Event> events ->
                            render(json([events: events.size()]))
                        }, {
                            log.error("Failed to stream", it)
                            response.status(400)
                            render(json([:]))
                        })
                    }
                }
            }
            path(":id") {
                byMethod {
                    post {
                        flow(parse(UpdateListCommand), BackpressureStrategy.ERROR)
                            .flatMap({clientManagementService.handle(it)})
                            .map({webSocketProcessorService.transmit(it)})
                            .toList()
                            .subscribe({List<Event> events ->
                                render(json([events: events.size()]))
                        }, {
                            log.error("Failed to stream", it)
                            response.status(400)
                            render(json([:]))
                        })
                    }
                }
            }

            path(":id/items") {
                byMethod {
                    post {
                        flow(parse(CreateNewItemCommand), BackpressureStrategy.ERROR)
                        .flatMap({clientManagementService.handle(it)})
                        .map({webSocketProcessorService.transmit(it)})
                        .toList()
                        .subscribe({render json(it)}, {
                            log.error("Failed to stream", it)
                            response.status(400)
                            render(json([:]))
                        })
                    }
                }
            }

            path(":id/items/:itemId") {
                byMethod {
                    post {
                        // Ratpack should really me to specify an ObjectMapper for parsing
                        flow(parse(UpdateItemCommand), BackpressureStrategy.ERROR)
                        .flatMap({clientManagementService.handle(it)})
                        .map({webSocketProcessorService.transmit(it)})
                        .toList()
                        .subscribe({render json(it)}, {
                            log.error("Failed to stream", it)
                            response.status(400)
                            render(json([:]))
                        })

                    }

                }
            }

        })
    }
}
