package demo.reactiveratpack.domain

import org.reactivestreams.Publisher


interface EventRepository {

    /**
     * Saves the events, then
     *
     */
    void save(List<Event> events)


    Publisher<Event> loadEventsForEntity(EntityStreamIdentifier identifier)

}