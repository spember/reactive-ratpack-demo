package demo.reactiveratpack.modules.todo

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Scopes
import com.google.inject.Singleton
import demo.reactiveratpack.domain.EventRepository
import demo.reactiveratpack.todo.CommandHandlingService
import demo.reactiveratpack.todo.EventInMemoryRepository
import demo.reactiveratpack.todo.EventReceiverService
import demo.reactiveratpack.todo.TodoItemInMemoryRepository
import demo.reactiveratpack.todo.TodoItemRepository
import demo.reactiveratpack.todo.TodoListInMemoryRepository
import demo.reactiveratpack.todo.TodoListRepository
import groovy.transform.CompileStatic

@CompileStatic
class TodoModule extends AbstractModule {
    @Override
    protected void configure() {
        [
                TodoApiUrlMappings

        ].each { Class aClass ->
            bind(aClass).in(Scopes.SINGLETON)
        }

    }

    @Provides
    @Singleton
    TodoListRepository todoListRepository() {
        new TodoListInMemoryRepository()
    }

    @Provides
    @Singleton
    TodoItemRepository todoItemRepository() {
        new TodoItemInMemoryRepository()
    }

    @Provides
    @Singleton
    EventRepository eventRepository() {
        new EventInMemoryRepository()
    }

    @Provides
    @Singleton
    CommandHandlingService commandHandlingService(TodoListRepository todoListRepository,
                                                   TodoItemRepository todoItemRepository,
                                                   EventRepository eventRepository) {
        return new CommandHandlingService(todoListRepository, todoItemRepository, eventRepository)
    }


    @Provides
    @Singleton
    EventReceiverService eventReceiverService(TodoListRepository todoListRepository,
                                                TodoItemRepository todoItemRepository,
                                                EventRepository eventRepository) {
        return new EventReceiverService(todoListRepository, todoItemRepository, eventRepository)
    }


}
