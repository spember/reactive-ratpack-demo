package demo.reactiveratpack.todo

import demo.reactiveratpack.domain.EntityStreamIdentifier
import demo.reactiveratpack.domain.Event
import demo.reactiveratpack.domain.EventRepository
import groovy.transform.CompileStatic
import io.reactivex.Flowable
import org.reactivestreams.Publisher

@CompileStatic
class EventInMemoryRepository implements EventRepository {
    private Map<EntityStreamIdentifier, List<Event>> store = [:]

    @Override
    Publisher<Event> save(final List<Event> events) {
        events.forEach({Event event ->
            if (!store.containsKey(event.getEntity())) {
                store[event.getEntity()] = []
            }
            store[event.getEntity()].add(event)
        })
        Flowable.fromIterable(events)
    }

    @Override
    Publisher<Event> loadEventsForEntity(final EntityStreamIdentifier identifier) {
        Flowable.fromIterable(store[identifier])
    }
}
