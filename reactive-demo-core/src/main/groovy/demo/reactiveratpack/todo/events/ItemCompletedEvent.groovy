package demo.reactiveratpack.todo.events

import demo.reactiveratpack.domain.Event
import demo.reactiveratpack.domain.EntityStreamIdentifier
import demo.reactiveratpack.user.UserId

import java.time.LocalDateTime


class ItemCompletedEvent extends Event {

    ItemCompletedEvent(final EntityStreamIdentifier entityIdentifier, final int revision,
                       final LocalDateTime dateCreated, final UserId userId) {
        super(entityIdentifier, revision, dateCreated, userId)
    }
}
