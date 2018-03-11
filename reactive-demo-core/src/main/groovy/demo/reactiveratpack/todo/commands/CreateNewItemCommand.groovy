package demo.reactiveratpack.todo.commands

import demo.reactiveratpack.user.UserId
import groovy.transform.CompileStatic

@CompileStatic
class CreateNewItemCommand {
    final UserId userId

    CreateNewItemCommand() {
        this.userId = new UserId("system")
    }
}
