package demo.reactiveratpack.todo

import demo.reactiveratpack.domain.EntityWithEvents
import demo.reactiveratpack.domain.Event
import demo.reactiveratpack.domain.EventRepository
import demo.reactiveratpack.todo.commands.CreateNewItemCommand
import demo.reactiveratpack.todo.commands.CreateNewListCommand
import demo.reactiveratpack.todo.commands.UpdateItemCommand
import demo.reactiveratpack.todo.commands.UpdateListCommand
import demo.reactiveratpack.todo.events.ItemAddedEvent
import demo.reactiveratpack.todo.events.ItemCompletedEvent
import demo.reactiveratpack.todo.events.ItemCreatedEvent
import demo.reactiveratpack.todo.events.ItemTextChangedEvent
import demo.reactiveratpack.todo.events.ListCreatedEvent
import demo.reactiveratpack.todo.events.ListNameUpdatedEvent
import groovy.transform.CompileStatic
import io.reactivex.Flowable
import org.reactivestreams.Publisher

import java.time.LocalDateTime

/**
 * Used by the Client app to handle
 */
@CompileStatic
class CommandHandlingService {

    private final TodoListRepository todoListRepository
    private final TodoItemRepository todoItemRepository
    private final EventRepository eventRepository

    CommandHandlingService(final TodoListRepository todoListRepository, final TodoItemRepository todoItemRepository,
                           final EventRepository eventRepository) {

        this.todoListRepository = todoListRepository
        this.todoItemRepository = todoItemRepository
        this.eventRepository = eventRepository
    }

    /**
     * Handles the creation of a new List
     *
     * @param command
     * @return
     */
    Publisher<Event> handle(CreateNewListCommand command) {
        ListId id = todoListRepository.getNextId()

        EntityWithEvents<TodoList> entityWithEvents = EntityWithEvents.builder()
                .withEntity(new TodoList(id))
                // creating a new list, so we know we start with 1
                .addEvent(new ListCreatedEvent(id, 1,
                    LocalDateTime.now(), command.userId))
                .addEvent(new ListNameUpdatedEvent(id, 2,
                    LocalDateTime.now(), command.userId, command.getName()))
        .build()
        todoListRepository.save(entityWithEvents.entity)
        eventRepository.save(entityWithEvents.events)
    }

    Publisher<Event> handle(UpdateListCommand command) {
        Flowable.fromPublisher(todoListRepository.get(command.getListId()))
        .map({ TodoList list -> EntityWithEvents.builder()
            .withEntity(list)
            .addEvent(new ListNameUpdatedEvent(list.id, list.revision+1, LocalDateTime.now(),
                command.userId, command.getName()))
            .build()
        })
        .flatMap({EntityWithEvents<TodoList> ewe ->
            todoListRepository.save(ewe.entity)
            eventRepository.save(ewe.events)
        }) as Publisher<Event>
    }

    Publisher<Event> handle(CreateNewItemCommand command) {
        ItemId itemId = todoItemRepository.getNextId()

        // messy, but functional
        Flowable.zip(Flowable.just(new TodoItem(itemId))
        .map({TodoItem item -> EntityWithEvents.builder()
            .withEntity(item)
            .addEvent(new ItemCreatedEvent(item.id, item.revision+1, LocalDateTime.now(), command.getUserId()))
            .build()
        })
        .flatMap({EntityWithEvents<TodoItem> ewe ->
            todoItemRepository.save(ewe.entity)
            eventRepository.save(ewe.events)
        }),

        Flowable.fromPublisher(todoListRepository.get(command.getListId()))
        .map({ TodoList list ->
            EntityWithEvents.builder()
                .withEntity(list)
                .addEvent(new ItemAddedEvent(list.id, list.revision+1, LocalDateTime.now(), command.userId, itemId))
                .build()
        })
        .flatMap({EntityWithEvents<TodoList> ewe ->
            todoListRepository.save(ewe.entity)
            eventRepository.save(ewe.events)
        }), { Event event1, Event event2 ->
            Flowable.just(event1, event2)
        })
        .flatMap({it}) as Publisher<Event>
    }

    Publisher<Event> handle(UpdateItemCommand command) {
        Flowable.fromPublisher(todoItemRepository.get(command.id))
        .map({ TodoItem item ->
            int revision = item.revision
            EntityWithEvents.Builder builder = EntityWithEvents.builder()
            .withEntity(item)


            if (command.getText()) {
                revision++
                builder.addEvent(new ItemTextChangedEvent(item.id, revision, LocalDateTime.now(), command.userId, command.getText()))
            }

            if (command.getComplete()) {
                revision++
                builder.addEvent(new ItemCompletedEvent(item.id, revision, LocalDateTime.now(), command.userId))
            }

            builder.build()
        })
        .flatMap({EntityWithEvents<TodoItem> ewe ->
            todoItemRepository.save(ewe.entity)
            eventRepository.save(ewe.events)
        }) as Publisher<Event>
    }
}
