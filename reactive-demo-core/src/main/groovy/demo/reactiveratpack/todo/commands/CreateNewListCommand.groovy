package demo.reactiveratpack.todo.commands

import com.fasterxml.jackson.annotation.JsonProperty
import demo.reactiveratpack.user.UserId
import groovy.transform.CompileStatic

@CompileStatic
class CreateNewListCommand {
    final UserId userId
    final String name

    CreateNewListCommand(final UserId userId, final String name) {
        this.userId = userId
        this.name = name
    }

    // dislike adding jsonProperties here, but too lazy to create my own
    // objectmapper using parameter names module and to add the compilation flag for a demo
    CreateNewListCommand(@JsonProperty("name")final String name) {
        this.userId = new UserId("system")
        this.name = name
    }
}
