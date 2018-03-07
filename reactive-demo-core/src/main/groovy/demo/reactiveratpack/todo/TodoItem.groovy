package demo.reactiveratpack.todo

import demo.reactiveratpack.domain.RevisionCapableEntity
import demo.reactiveratpack.todo.events.ItemCompletedEvent
import demo.reactiveratpack.todo.events.ItemCreatedEvent
import demo.reactiveratpack.todo.events.ItemTextChangedEvent
import groovy.transform.CompileStatic

import java.time.LocalDateTime

@CompileStatic
class TodoItem implements RevisionCapableEntity {

    final ItemId id
    String text
    boolean complete = false

    LocalDateTime dateCreated

    TodoItem(final ItemId id) {
        this.id = id
    }

    boolean apply(ItemCreatedEvent event) {
        maybeApply(event, {ItemCreatedEvent e ->
            this.dateCreated = e.dateCreated
        })
    }

    boolean apply(ItemTextChangedEvent event) {
        maybeApply(event, {ItemTextChangedEvent e ->
            this.text = e.text
        })
    }

    boolean apply(ItemCompletedEvent event) {
        maybeApply(event, {ItemCompletedEvent e ->
            this.complete = true
        })
    }
}
