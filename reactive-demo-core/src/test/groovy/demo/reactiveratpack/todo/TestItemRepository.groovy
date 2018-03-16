package demo.reactiveratpack.todo

import org.reactivestreams.Publisher

class TestItemRepository implements TodoItemRepository {
    @Override
    ItemId getNextId() {
        new ItemId(UUID.randomUUID().toString())
    }

    @Override
    void save(final TodoItem todoItem) {

    }

    @Override
    Publisher<TodoItem> get(final ItemId itemId) {
        return null
    }

    @Override
    Publisher<TodoItem> findAll(final ItemId... itemIds) {
        return null
    }
}
