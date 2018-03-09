package demo.reactiveratpack.modules.todo

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Scopes
import com.google.inject.Singleton
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
}
