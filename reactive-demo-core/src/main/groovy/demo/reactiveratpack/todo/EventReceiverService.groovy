package demo.reactiveratpack.todo

import demo.reactiveratpack.domain.EntityWithEvents
import demo.reactiveratpack.domain.Event
import demo.reactiveratpack.domain.EventRepository
import demo.reactiveratpack.todo.events.ListCreatedEvent
import demo.reactiveratpack.todo.events.ListNameUpdatedEvent
import groovy.transform.CompileStatic
import io.reactivex.Flowable
import org.reactivestreams.Publisher

import java.time.LocalDateTime

/**
 * For Reacting to external events.
 */
@CompileStatic
class EventReceiverService {
    private final TodoListRepository todoListRepository
    private final TodoItemRepository todoItemRepository
    private final EventRepository eventRepository

    EventReceiverService(
            final TodoListRepository todoListRepository,
            final TodoItemRepository todoItemRepository, final EventRepository eventRepository) {
        this.todoListRepository = todoListRepository
        this.todoItemRepository = todoItemRepository
        this.eventRepository = eventRepository
    }

    /**
     * Signals that a list was created outside the service, and we must replicate the
     * creation in this one.
     *
     * @param event
     * @return
     */
    Publisher<Event> receive(ListCreatedEvent event) {
        ListId id = event.entity as ListId
        EntityWithEvents<TodoList> entityWithEvents = EntityWithEvents.builder()
                .withEntity(new TodoList(id))
                .addEvent(new ListCreatedEvent(id, 1,
                    LocalDateTime.now(), event.userId))
                .build()
        todoListRepository.save(entityWithEvents.entity)
        eventRepository.save(entityWithEvents.events)
    }

    Publisher<Event> receive(ListNameUpdatedEvent event) {
        ListId id = event.entity as ListId
        Flowable.fromPublisher(todoListRepository.get(id))
        .map({TodoList list -> EntityWithEvents.builder()
                .withEntity(list)
                .addEvent(new ListNameUpdatedEvent(list.id, list.revision+1, LocalDateTime.now(),
                event.userId, event.getName()))
                .build()
        })
        .flatMap({EntityWithEvents<TodoList> ewe ->
            todoListRepository.save(ewe.entity)
            eventRepository.save(ewe.events)
        })
    }


}
