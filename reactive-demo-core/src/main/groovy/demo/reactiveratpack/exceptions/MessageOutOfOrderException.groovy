package demo.reactiveratpack.exceptions

import groovy.transform.CompileStatic

@CompileStatic
class MessageOutOfOrderException extends RuntimeException {

    MessageOutOfOrderException(final String message) {
        super(message)
    }
}
