package demo.reactiveratpack.todo.commands

import demo.reactiveratpack.user.UserId


class CreateNewListCommand {
    final UserId userId
    final String name

    CreateNewListCommand(final UserId userId, final String name) {
        this.userId = userId
        this.name = name
    }
}
