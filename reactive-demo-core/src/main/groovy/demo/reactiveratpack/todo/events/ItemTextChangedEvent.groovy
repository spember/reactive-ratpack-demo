package demo.reactiveratpack.todo.events

import demo.reactiveratpack.domain.Event
import demo.reactiveratpack.domain.EntityStreamIdentifier
import demo.reactiveratpack.user.UserId
import groovy.transform.CompileStatic

import java.time.LocalDateTime

@CompileStatic
class ItemTextChangedEvent extends Event {
    private final String text

    ItemTextChangedEvent(final EntityStreamIdentifier entityId, final int revision,
                         final LocalDateTime dateCreated,
                         final UserId userId, final String text) {
        super(entityId, revision, dateCreated, userId)
        this.text = text
    }

    String getText() {
        return text
    }
}
