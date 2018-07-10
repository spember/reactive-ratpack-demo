package demo.reactiveratpack.exceptions

import groovy.transform.CompileStatic

@CompileStatic
class UuidParsingException extends RuntimeException {
    UuidParsingException(final String message) {
        super(message)
    }
}
