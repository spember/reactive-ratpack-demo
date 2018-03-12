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
        // Validation inside the command constructor + final vars = object is never in an invalid state!
        if (!userId || !name) {
            throw new RuntimeException("Invalid List creation: missing values")
        }
    }

    // dislike adding jsonProperties here, but too lazy to create my own
    // objectmapper using parameter names module and to add the compilation flag for a demo
    CreateNewListCommand(@JsonProperty("name")final String name) {
        this(new UserId("system"), name)
    }
}
