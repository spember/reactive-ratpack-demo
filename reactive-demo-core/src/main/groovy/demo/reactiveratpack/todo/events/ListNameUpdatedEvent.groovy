package demo.reactiveratpack.todo.events

import demo.reactiveratpack.domain.Event
import demo.reactiveratpack.user.UserId
import demo.reactiveratpack.todo.ListId
import groovy.transform.CompileStatic

import java.time.LocalDateTime


@CompileStatic
class ListNameUpdatedEvent extends Event{
    final String name

    ListNameUpdatedEvent(final ListId id, final int revision, final LocalDateTime dateCreated, final UserId userId, final String name) {
        super(id, revision, dateCreated, userId)
        this.name = name
    }
}
