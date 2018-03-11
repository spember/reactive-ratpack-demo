package demo.reactiveratpack.todo.commands

import com.fasterxml.jackson.annotation.JsonProperty
import demo.reactiveratpack.todo.ListId
import demo.reactiveratpack.user.UserId
import groovy.transform.CompileStatic

@CompileStatic
class UpdateListCommand {
    final UserId userId
    final ListId listId
    final String name
    // any other fields? Probably no


    UpdateListCommand(final UserId userId, final ListId listId, final String name) {
        this.userId = userId
        this.listId = listId
        this.name = name
    }

    UpdateListCommand(@JsonProperty("id")final ListId listId, @JsonProperty("name")final String name) {
        this.listId = listId
        this.name = name
        this.userId = new UserId("system")
    }
}
