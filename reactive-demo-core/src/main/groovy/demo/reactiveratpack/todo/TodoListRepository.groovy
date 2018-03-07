package demo.reactiveratpack.todo

import demo.reactiveratpack.user.UserId
import groovy.transform.CompileStatic
import org.reactivestreams.Publisher

@CompileStatic
interface TodoListRepository {

    ListId getNextId();

    void save(TodoList todoList)

    Publisher<TodoList> get(ListId id)

    Publisher<TodoList> list()

    Publisher<TodoList> findAllForOwner(UserId userId)

}
