package demo.reactiveratpack.domain

import org.reactivestreams.Publisher


interface EventRepository {

    /**
     * Saves the events, then returns them
     *
     */
    Publisher<Event> save(List<Event> events)


    Publisher<Event> loadEventsForEntity(EntityStreamIdentifier identifier)

}