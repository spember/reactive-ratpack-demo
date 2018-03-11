package demo.reactiveratpack.todo.commands

import demo.reactiveratpack.todo.ItemId
import demo.reactiveratpack.todo.ListId
import demo.reactiveratpack.user.UserId
import groovy.transform.CompileStatic

/**
 * Tracks both name change and completion
 */
@CompileStatic
class UpdateItemCommand {

    final ItemId id
    final String name
    final boolean complete
    final UserId userId

    
    UpdateItemCommand(final ItemId id, final String name, final boolean complete, final UserId userId) {
        this.id = id
        this.name = name
        this.complete = complete
        this.userId = userId
    }
}
