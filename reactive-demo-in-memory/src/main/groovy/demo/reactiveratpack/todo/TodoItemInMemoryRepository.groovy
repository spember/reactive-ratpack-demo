package demo.reactiveratpack.todo

import com.google.common.collect.ImmutableList
import groovy.transform.CompileStatic
import io.reactivex.Flowable
import org.reactivestreams.Publisher

@CompileStatic
class TodoItemInMemoryRepository implements TodoItemRepository {
    Map<ItemId, TodoItem> store = [:]

    @Override
    ItemId getNextId() {
        new ItemId(UUID.randomUUID().toString())
    }

    @Override
    void save(final TodoItem todoItem) {
        store[todoItem.getId()] = todoItem
    }

    @Override
    Publisher<TodoItem> get(final ItemId itemId) {
        Flowable.just(store[itemId])
    }

    @Override
    Publisher<TodoItem> findAll(final ItemId... itemIds) {
        // really this should be a processor / subject
        List<TodoItem> items = []
        itemIds.each {ItemId id ->
            if (store.containsKey(id)) {
                items.add(store[id])
            }
        }
        Flowable.fromIterable(items)
    }
}
