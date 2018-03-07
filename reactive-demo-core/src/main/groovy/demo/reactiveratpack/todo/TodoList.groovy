package demo.reactiveratpack.todo

import com.google.common.collect.ImmutableList
import demo.reactiveratpack.domain.RevisionCapableEntity
import demo.reactiveratpack.todo.events.ItemAddedEvent
import demo.reactiveratpack.todo.events.ItemRemovedEvent
import demo.reactiveratpack.todo.events.ListCreatedEvent
import demo.reactiveratpack.todo.events.ListNameUpdatedEvent
import demo.reactiveratpack.user.UserId
import groovy.transform.CompileStatic

import java.time.LocalDateTime

/**
 * An Entity representing many {@link TodoItem}s.
 */
@CompileStatic
class TodoList implements RevisionCapableEntity {
    final ListId id
    String name
    UserId owner
    LocalDateTime dateCreated
    LocalDateTime lastUpdated
    ImmutableList<ItemId> items = ImmutableList.of() // list of attached todo items

    TodoList(final ListId id) {
        this.id = id
    }

    TodoList apply(final ListCreatedEvent event) {
        // #maybeApply checks revision number and either throws an
        // out-of-order exception or updates to the new revision
        maybeApply(event, {ListCreatedEvent e ->
            this.dateCreated = e.dateCreated
            this.lastUpdated = e.dateCreated
            this.owner = e.userId
        })
        this
    }

    TodoList apply(final ListNameUpdatedEvent event) {
        maybeApply(event, {ListNameUpdatedEvent e ->
            this.lastUpdated = e.dateCreated
            this.name = e.name
        })
        this
    }

    TodoList apply(final ItemAddedEvent event) {
        maybeApply(event, {ItemAddedEvent e ->
            ImmutableList.Builder<ItemId> itemsBuilder = ImmutableList.builder()
            items.each {ItemId id -> itemsBuilder.add(id)}
            itemsBuilder.add(e.itemId)
            items = itemsBuilder.build()
            this.lastUpdated = e.dateCreated
        })
        this
    }

    TodoList apply(final ItemRemovedEvent event) {
        maybeApply(event, {ItemRemovedEvent e ->
            ImmutableList.Builder<ItemId> itemsBuilder = ImmutableList.builder()
            items.each {ItemId id -> if (id != e.itemId) {
                itemsBuilder.add(id)
            }}
            items = itemsBuilder.build()
        })
        this
    }



}
