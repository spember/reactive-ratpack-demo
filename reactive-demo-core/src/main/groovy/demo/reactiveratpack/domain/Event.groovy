package demo.reactiveratpack.domain

import demo.reactiveratpack.user.UserId
import groovy.transform.CompileStatic

import java.time.LocalDateTime

@CompileStatic
abstract class Event {
    private final EntityStreamIdentifier entity
    private final int revision
    private final LocalDateTime dateCreated
    private final UserId userId

    Event(final EntityStreamIdentifier entityIdentifier, final int revision, final LocalDateTime dateCreated,
          final UserId userId) {
        this.entity = entityIdentifier
        this.revision = revision
        this.dateCreated = dateCreated
        this.userId = userId
    }

    EntityStreamIdentifier getEntity() {
        return entity
    }

    int getRevision() {
        return revision
    }

    LocalDateTime getDateCreated() {
        return dateCreated
    }

    UserId getUserId() {
        return userId
    }
}
