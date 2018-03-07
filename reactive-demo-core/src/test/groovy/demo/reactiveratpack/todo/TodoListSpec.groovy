package demo.reactiveratpack.todo

import demo.reactiveratpack.todo.events.ItemAddedEvent
import demo.reactiveratpack.todo.events.ItemCompletedEvent
import demo.reactiveratpack.todo.events.ItemCreatedEvent
import demo.reactiveratpack.todo.events.ItemRemovedEvent
import demo.reactiveratpack.todo.events.ItemTextChangedEvent
import demo.reactiveratpack.todo.events.ListCreatedEvent
import demo.reactiveratpack.todo.events.ListNameUpdatedEvent
import demo.reactiveratpack.exceptions.MessageOutOfOrderException
import demo.reactiveratpack.user.UserId
import demo.reactiveratpack.todo.ItemId
import demo.reactiveratpack.todo.ListId
import demo.reactiveratpack.todo.TodoItem
import demo.reactiveratpack.todo.TodoList
import spock.lang.Specification

import java.time.LocalDateTime


class TodoListSpec extends Specification {

    void "Test of applying events to Lists"() {
        given:
        UserId currentUser = new UserId("bob")
        ListId listId = new ListId("list-123")

        when:
        TodoList mainList = new TodoList(listId)
        mainList.apply(new ListCreatedEvent(mainList.id, 1, LocalDateTime.now(), currentUser))
        mainList.apply(new ListNameUpdatedEvent(mainList.id, 2, LocalDateTime.now(), currentUser, "My lirsd"))
        mainList.apply(new ListNameUpdatedEvent(mainList.id, 3, LocalDateTime.now(), currentUser, "My first list"))


        then:
        mainList.getName() == "My first list"
        mainList.getOwner() == currentUser
        mainList.getDateCreated() != null
        mainList.getLastUpdated() != null
        mainList.getRevision() == 3
    }

    void "Adding out of order should throw an exception" () {
        given:
        UserId currentUser = new UserId("bob")
        ListId listId = new ListId("list-123")

        when:
        TodoList mainList = new TodoList(listId)
        mainList.apply(new ListCreatedEvent(listId, 1, LocalDateTime.now(), currentUser))
        mainList.apply(new ListNameUpdatedEvent(listId, 10, LocalDateTime.now(), currentUser, "Custom Name"))

        then:
        thrown(MessageOutOfOrderException)
        mainList.name == null
    }

    void "Sample event flow, creating an item and adding it" () {
        given:
        UserId currentUser = new UserId("ralph")

        when:
        ListId listId = new ListId("list-1")
        TodoList mainList = new TodoList(listId)
        mainList.apply(new ListCreatedEvent(listId, 1, LocalDateTime.now(), currentUser))
        mainList.apply(new ListNameUpdatedEvent(listId, 2, LocalDateTime.now(), currentUser, "Sample List"))


        TodoItem item1 = new TodoItem(new ItemId("item-1"))
        item1.apply(new ItemCreatedEvent(item1.id, 1, LocalDateTime.now(), currentUser))
        item1.apply(new ItemTextChangedEvent(item1.id, 2, LocalDateTime.now(), currentUser, "Foo"))
        item1.apply(new ItemTextChangedEvent(item1.id, 3, LocalDateTime.now(), currentUser, "Read a book"))

        TodoItem item2 = new TodoItem(new ItemId("item-2"))
        item2.apply(new ItemCreatedEvent(item2.id, 1, LocalDateTime.now(), currentUser))
        item2.apply(new ItemTextChangedEvent(item2.id, 2, LocalDateTime.now(), currentUser, "Take the dog for a walk"))

        TodoItem item3 = new TodoItem(new ItemId("item-3"))
        item3.apply(new ItemCreatedEvent(item3.id, 1, LocalDateTime.now(), currentUser))
        item3.apply(new ItemTextChangedEvent(item3.id, 2, LocalDateTime.now(), currentUser, "Read a book"))

        item1.apply(new ItemCompletedEvent(item1.id, 4, LocalDateTime.now(), currentUser))

        mainList.apply(new ItemAddedEvent(mainList.id, 3, LocalDateTime.now(), currentUser, item1.id))
        mainList.apply(new ItemAddedEvent(mainList.id, 4, LocalDateTime.now(), currentUser, item2.id))
        mainList.apply(new ItemAddedEvent(mainList.id, 5, LocalDateTime.now(), currentUser, item3.id))

        mainList.apply(new ItemRemovedEvent(mainList.id, 6, LocalDateTime.now(), currentUser, item2.id))

        then:
        mainList.name == "Sample List"
        mainList.items.size() == 2
        mainList.items.find {it == item1.id} != null
        mainList.items.find {it == item3.id} != null

    }
}
