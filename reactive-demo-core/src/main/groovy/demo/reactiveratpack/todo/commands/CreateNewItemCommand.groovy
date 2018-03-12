package demo.reactiveratpack.todo.commands

import com.fasterxml.jackson.annotation.JsonProperty
import demo.reactiveratpack.todo.ListId
import demo.reactiveratpack.user.UserId
import groovy.transform.CompileStatic

@CompileStatic
class CreateNewItemCommand {
    final UserId userId
    final ListId listId

    CreateNewItemCommand(final UserId userId, final ListId listId) {
        this.userId = userId
        this.listId = listId
        if (!userId || !listId) {
            throw  new RuntimeException("Command is missing required props")
        }
    }

    CreateNewItemCommand(@JsonProperty("listId") final ListId listId) {
        this(new UserId("system"), listId)
    }
}
