package demo.reactiveratpack.todo

import groovy.transform.CompileStatic
import org.reactivestreams.Publisher

@CompileStatic
interface TodoItemRepository {

    ItemId getNextId();

    void save(TodoItem todoItem)

    Publisher<TodoItem> get(ItemId itemId)

    Publisher<TodoItem> findAll(ItemId... itemIds)

}