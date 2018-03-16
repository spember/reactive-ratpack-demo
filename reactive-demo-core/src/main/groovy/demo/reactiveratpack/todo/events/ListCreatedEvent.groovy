package demo.reactiveratpack.todo.events

import com.fasterxml.jackson.annotation.JsonProperty
import demo.reactiveratpack.domain.Event
import demo.reactiveratpack.user.UserId
import demo.reactiveratpack.todo.ListId
import groovy.transform.CompileStatic

import java.time.LocalDateTime

@CompileStatic
class ListCreatedEvent extends Event {
    ListCreatedEvent(final ListId id, final int revision, final LocalDateTime dateCreated, final UserId userId) {
        super(id, revision, dateCreated, userId)
    }

    ListCreatedEvent(@JsonProperty("id")final ListId id, @JsonProperty("revision")final int revision, @JsonProperty("userId")final UserId userId) {
        super(id, revision, LocalDateTime.now(), userId)
    }
}
