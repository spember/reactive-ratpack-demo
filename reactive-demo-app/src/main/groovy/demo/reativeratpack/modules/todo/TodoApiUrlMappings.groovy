package demo.reativeratpack.modules.todo

import com.google.inject.Inject
import demo.reactiveratpack.todo.TodoList
import demo.reactiveratpack.todo.TodoListRepository
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.reactivex.Flowable
import ratpack.groovy.handling.GroovyChainAction

import static ratpack.jackson.Jackson.json

@Slf4j
@CompileStatic
class TodoApiUrlMappings extends GroovyChainAction {
    private TodoListRepository todoListRepository

    @Inject
    TodoApiUrlMappings(final TodoListRepository todoListRepository) {
        this.todoListRepository = todoListRepository
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
                    }
                }
            }
        })
    }
}
