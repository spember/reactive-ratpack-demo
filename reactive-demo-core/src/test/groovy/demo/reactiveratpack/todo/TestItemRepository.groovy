package demo.reactiveratpack.todo

class TestItemRepository implements TodoItemRepository {
    @Override
    ItemId getNextId() {
        new ItemId(UUID.randomUUID().toString())
    }
}
