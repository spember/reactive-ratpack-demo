package demo.reactiveratpack.todo.events

import demo.reactiveratpack.domain.Event
import demo.reactiveratpack.user.UserId
import demo.reactiveratpack.todo.ItemId
import demo.reactiveratpack.todo.ListId
import groovy.transform.CompileStatic

import java.time.LocalDateTime

@CompileStatic
class ItemAddedEvent extends Event {
    final ItemId itemId

    ItemAddedEvent(final ListId id, final int revision, final LocalDateTime dateCreated,
                   final UserId userId, final ItemId itemId) {
        super(id, revision, dateCreated, userId)
        this.itemId = itemId
    }
}
