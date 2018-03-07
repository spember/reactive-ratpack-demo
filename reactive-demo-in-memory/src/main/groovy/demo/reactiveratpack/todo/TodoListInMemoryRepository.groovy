package demo.reactiveratpack.todo

import demo.reactiveratpack.user.UserId
import groovy.transform.CompileStatic
import io.reactivex.Flowable
import org.reactivestreams.Publisher

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
