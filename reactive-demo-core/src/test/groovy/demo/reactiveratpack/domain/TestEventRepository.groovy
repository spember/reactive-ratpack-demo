package demo.reactiveratpack.domain

import io.reactivex.Flowable
import org.reactivestreams.Publisher


class TestEventRepository implements EventRepository {

    private Map<EntityStreamIdentifier, List<Event>> cache = [:]

    @Override
    void save(final List<Event> events) {
        events.each {
            if (!cache.containsKey(it.entity)){
                cache[it.entity] = []
            }
            cache[it.entity].add(it)

        }
    }

    @Override
    Publisher<Event> loadEventsForEntity(final EntityStreamIdentifier identifier) {
        Flowable.fromIterable(cache[identifier])
    }
}
