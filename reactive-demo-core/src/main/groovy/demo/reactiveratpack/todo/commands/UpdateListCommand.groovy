package demo.reactiveratpack.todo.commands

import com.fasterxml.jackson.annotation.JsonProperty
import demo.reactiveratpack.todo.ListId
import demo.reactiveratpack.user.UserId
import groovy.transform.CompileStatic
import org.apache.commons.lang.StringUtils

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

        if (!userId || !listId) {
            throw new RuntimeException("Invalid List update: missing values")
        }

        if (StringUtils.isBlank(name)) {
            throw new RuntimeException("Name cannot be blank")
        }
    }

    UpdateListCommand(@JsonProperty("id")final ListId listId, @JsonProperty("name")final String name) {
        this(new UserId("system"), listId, name)
    }
}
