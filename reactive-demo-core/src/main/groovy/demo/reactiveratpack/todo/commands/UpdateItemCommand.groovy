package demo.reactiveratpack.todo.commands

import com.fasterxml.jackson.annotation.JsonProperty
import demo.reactiveratpack.todo.ItemId
import demo.reactiveratpack.user.UserId
import groovy.transform.CompileStatic

/**
 * Tracks both name change and completion
 */
@CompileStatic
class UpdateItemCommand {

    final ItemId id
    final String text
    final Boolean complete
    final UserId userId


    UpdateItemCommand(final ItemId id, final String text, final Boolean complete, final UserId userId) {
        this.id = id
        this.text = text
        this.complete = complete
        this.userId = userId
    }

    UpdateItemCommand(@JsonProperty("id")final ItemId id, @JsonProperty("text")final String text,
                      @JsonProperty("complete")final Boolean complete) {
        this.id = id
        this.text = text
        this.complete = complete
        this.userId = new UserId("system")
    }
}
