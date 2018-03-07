package demo.reactiveratpack.todo

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
}
