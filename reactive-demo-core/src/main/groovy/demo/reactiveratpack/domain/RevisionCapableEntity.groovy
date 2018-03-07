package demo.reactiveratpack.domain

import demo.reactiveratpack.exceptions.MessageOutOfOrderException
import groovy.transform.CompileStatic

import java.util.function.Consumer

@CompileStatic
trait RevisionCapableEntity {

    int revision = 0

    boolean canApply(final int incomingRevision) {
        incomingRevision == this.revision + 1;
    }

    public <E extends Event> boolean maybeApply(final E event, Consumer<E> applier) {

        if (!canApply(event.getRevision())) {
            throw new MessageOutOfOrderException("Error while applying event ${event.getClass().name}: " +
                    "Expected revision ${this.revision+1}, but was ${event.revision}")
        }
        applier.accept(event)
        revision = event.getRevision()
    }

    public <E extends Event> RevisionCapableEntity apply(final E event) {

    }

}