package demo.reactiveratpack.todo

import demo.reactiveratpack.user.UserId
import io.reactivex.Flowable
import org.reactivestreams.Publisher


class TestListRepository implements TodoListRepository {
    private Map<ListId, TodoList> cache = [:]
    @Override
    ListId getNextId() {
        new ListId(UUID.randomUUID().toString())
    }

    @Override
    void save(final TodoList todoList) {
        cache[todoList.id] = todoList
    }

    @Override
    Publisher get(final ListId id) {
        Flowable.just(cache[id])
    }

    @Override
    Publisher<TodoList> list() {
        Flowable.fromIterable(cache.values())
    }

    @Override
    Publisher<TodoList> findAllForOwner(final UserId userId) {
        return null
    }

    int count () {
        cache.values().size()
    }
}
