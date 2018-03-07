package demo.reactiveratpack.todo

import com.sun.tools.javac.comp.Todo
import demo.reactiveratpack.amqp.MessageBroadCastService
import demo.reactiveratpack.domain.EntityWithEvents
import demo.reactiveratpack.domain.Event
import demo.reactiveratpack.domain.EventRepository
import demo.reactiveratpack.todo.commands.CreateNewListCommand
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
class ClientManagementService {

    private final TodoListRepository todoListRepository
    private final TodoItemRepository todoItemRepository
    private final EventRepository eventRepository

    ClientManagementService(final TodoListRepository todoListRepository, final TodoItemRepository todoItemRepository,
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
        // very rough validation
        if (!command.userId || !command.name) {
            throw new RuntimeException("Invalid List creation: missing values")
        }

        ListId id = todoListRepository.getNextId()

        EntityWithEvents<TodoList> entityWithEvents = EntityWithEvents.builder()
                .withEntity(new TodoList(id))
                // creating a new list, so we know we start with 1
                .addEvent(new ListCreatedEvent(id, 1,
                    LocalDateTime.now(), command.userId))
                .addEvent(new ListNameUpdatedEvent(id, 2,
                    LocalDateTime.now(), command.userId, command.getName()))
        .build()

        //
        // A *really* good question to ask later would be:
        // "What happens if one of the following method calls fails?"
        //
        todoListRepository.save(entityWithEvents.entity)
        eventRepository.save(entityWithEvents.events)
//        messageBroadCastService.transmit(entityWithEvents.events)


        Flowable.fromIterable(entityWithEvents.events)
    }


}
