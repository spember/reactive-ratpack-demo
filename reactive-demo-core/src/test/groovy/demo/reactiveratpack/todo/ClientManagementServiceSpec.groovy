package demo.reactiveratpack.todo

import demo.reactiveratpack.domain.Event
import demo.reactiveratpack.domain.TestEventRepository
import demo.reactiveratpack.todo.commands.CreateNewItemCommand
import demo.reactiveratpack.todo.commands.CreateNewListCommand
import demo.reactiveratpack.todo.commands.UpdateListCommand
import demo.reactiveratpack.todo.events.ListCreatedEvent
import demo.reactiveratpack.todo.events.ListNameUpdatedEvent
import demo.reactiveratpack.user.UserId
import io.reactivex.Flowable
import spock.lang.Specification

import java.nio.FloatBuffer


class ClientManagementServiceSpec extends Specification {

    ClientManagementService service

    TestItemRepository testItemRepository
    TestListRepository testListRepository

    UserId userId

    def setup() {
        testItemRepository = new TestItemRepository()
        testListRepository = new TestListRepository()
        service = new ClientManagementService(testListRepository,
                testItemRepository, new TestEventRepository())
        userId = new UserId("Test Testington")
    }

    def "valid command should create events" () {
        given:
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

        testListRepository.count() == 1
    }

    def "valid command should update name" () {
        when:
        List creationEvents = Flowable.fromPublisher(service.handle(new CreateNewListCommand(userId, "Sample List 1")))
        .test()
        .values()

        ListCreatedEvent createdEvent = creationEvents[0] as ListCreatedEvent

        List nameChangeEvents = Flowable.fromPublisher(service.handle(new UpdateListCommand(
                userId, createdEvent.getEntity() as ListId, "The Real List Name")))
        .test()
        .values()

        then:
        nameChangeEvents.size() == 1
        nameChangeEvents[0] instanceof ListNameUpdatedEvent
        nameChangeEvents[0].userId == userId
        testListRepository.count() == 1

        TodoList todoList = testListRepository.get(createdEvent.entity as ListId)
        .test().values()[0]

        todoList.name == "The Real List Name"
    }

    def "adding items should emit two events and update the list" () {
        given:
        List creationEvents = Flowable.fromPublisher(service.handle(new CreateNewListCommand(userId, "Sample List 1")))
                .test()
                .values()

        ListCreatedEvent createdEvent = creationEvents[0] as ListCreatedEvent
        TodoList list = testListRepository.get(createdEvent.entity as ListId)
        .test()
        .values()[0]

        when:
        List item1Events = Flowable.fromPublisher(service.handle(new CreateNewItemCommand(userId, list.id)))
        .test().values()


        List item2Events = Flowable.fromPublisher(service.handle(new CreateNewItemCommand(userId, list.id)))
                .test().values()

        then:

        item1Events.size() == 2
        item2Events.size() == 2



    }
}
