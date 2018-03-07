package demo.reactiveratpack.todo

import demo.reactiveratpack.domain.Event
import demo.reactiveratpack.domain.TestEventRepository
import demo.reactiveratpack.todo.commands.CreateNewListCommand
import demo.reactiveratpack.todo.events.ListCreatedEvent
import demo.reactiveratpack.todo.events.ListNameUpdatedEvent
import demo.reactiveratpack.user.UserId
import io.reactivex.Flowable
import spock.lang.Specification


class ClientManagementServiceSpec extends Specification {

    ClientManagementService service

    def setup() {
        service = new ClientManagementService(new TestListRepository(),
                new TestItemRepository(), new TestEventRepository())
    }

    def "valid command should create events" () {
        given:
        UserId userId = new UserId("bob")
        CreateNewListCommand command = new CreateNewListCommand(userId, "Sample List 1")

        when:
        List events = Flowable.fromPublisher(service.handle(command))
        .test()
        .values()

        then:
        events.size() == 2
        events[0] instanceof ListCreatedEvent
        events[0].userId == userId
        events[0].entity != null
        events[1] instanceof ListNameUpdatedEvent
        events[1].userId == userId
        events[1].entity == events[0].entity
    }
}
