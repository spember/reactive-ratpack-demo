package demo.reactiveratpack.todo

import com.fasterxml.jackson.databind.ObjectMapper
import demo.reactiveratpack.domain.EntityStreamIdentifier
import demo.reactiveratpack.domain.Event
import demo.reactiveratpack.domain.EventRepository
import groovy.transform.CompileStatic
import io.lettuce.core.api.StatefulRedisConnection
import io.reactivex.Flowable
import org.reactivestreams.Publisher

@CompileStatic
class EventRedisRepository implements EventRepository {
    private final StatefulRedisConnection<String, String> connection;
    private final ObjectMapper objectMapper

    EventRedisRepository(StatefulRedisConnection<String, String> connection, ObjectMapper objectMapper) {
        this.connection = connection
        this.objectMapper = objectMapper

    }

    @Override
    Publisher<Event> save(List<Event> events) {
        // sync for now, fix if possible
        Map<EntityStreamIdentifier, List<Event>> groups = events.groupBy {Event event -> event.entity}
        println "Have events ${events}"
        groups.keySet().each {EntityStreamIdentifier identifier ->
            println "Working on ${identifier.toString()}"
            String[] values = groups[identifier].collect({objectMapper.writeValueAsString(it)})
            connection.sync().lpush(identifier.toString(), values)
        }

        Flowable.fromIterable(events)
    }

    @Override
    Publisher<Event> loadEventsForEntity(EntityStreamIdentifier identifier) {
        //Flowable.fromIterable(connection.sync().lrange(identifier.toString(), 0, Long.MAX_VALUE).collect {objectMapper.readV})
    }
}
