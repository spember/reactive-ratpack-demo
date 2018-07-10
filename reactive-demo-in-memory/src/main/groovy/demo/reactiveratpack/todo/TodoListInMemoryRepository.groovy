package demo.reactiveratpack.todo

import demo.reactiveratpack.user.UserId
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.reactivex.Flowable
import org.reactivestreams.Publisher

@Slf4j
@CompileStatic
class TodoListInMemoryRepository implements TodoListRepository {

    private Map<ListId, TodoList> store = [:]

    @Override
    ListId getNextId() {
        new ListId(UUID.randomUUID().toString())
    }

    @Override
    void save(final TodoList todoList) {
        store[todoList.getId()] = todoList
    }

    @Override
    Publisher<TodoList> get(final ListId id) {
        println("HAve keys of ${store.keySet().collect({it.value})}")
        println "Looking for ${id.value}"
        Flowable.just(store[id])
    }

    @Override
    Publisher<TodoList> list() {
        Flowable.fromIterable(store.values())
    }

    @Override
    Publisher<TodoList> findAllForOwner(final UserId userId) {
        Flowable.fromIterable(store.values())
        .filter({TodoList list -> list.owner == userId})
    }
}
