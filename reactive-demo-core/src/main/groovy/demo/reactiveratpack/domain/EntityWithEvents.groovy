package demo.reactiveratpack.domain

import com.google.common.collect.ImmutableList


class EntityWithEvents<E extends RevisionCapableEntity> {
    private final E entity
    private final ImmutableList<? extends Event> events

    EntityWithEvents(final E entity, final ImmutableList<? extends Event> events) {
        if (!entity) {
            throw new IllegalArgumentException("Need an entity")
        }
        if (!events) {
            throw new IllegalArgumentException("Events cannot be null")
        }

        this.entity = entity
        this.events = events
        events.each {Event event ->
            entity.apply(event)
        }
    }

    E getEntity() {
        entity
    }

    ImmutableList<Event> getEvents() {
        events
    }

    static Builder builder() {
        new Builder()
    }

    static class Builder<E extends RevisionCapableEntity> {
        E entity
        ImmutableList<? extends Event> events

        Builder withEntity( entity) {
            this.entity = entity
            this
        }

        Builder addEvent(Event event) {
            ImmutableList.Builder builder = new ImmutableList<>.Builder()
            if (events) {
                builder.addAll(events)
            }
            builder.add(event)
            this.events = builder.build()
            this
        }

        EntityWithEvents build() {
            new EntityWithEvents(this.entity, this.events)
        }
    }
}
