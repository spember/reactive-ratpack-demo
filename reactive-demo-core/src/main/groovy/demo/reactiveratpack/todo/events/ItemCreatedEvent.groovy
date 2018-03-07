package demo.reactiveratpack.todo.events

import demo.reactiveratpack.domain.Event
import demo.reactiveratpack.domain.EntityStreamIdentifier
import demo.reactiveratpack.user.UserId
import groovy.transform.CompileStatic

import java.time.LocalDateTime

@CompileStatic
class ItemCreatedEvent extends Event {

    ItemCreatedEvent(final EntityStreamIdentifier entityIdentifier, final int revision,
                     final LocalDateTime dateCreated, final UserId userId) {
        super(entityIdentifier, revision, dateCreated, userId)
    }
}
