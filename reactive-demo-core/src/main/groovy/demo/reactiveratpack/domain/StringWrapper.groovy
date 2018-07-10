package demo.reactiveratpack.domain

import demo.reactiveratpack.exceptions.UuidParsingException
import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode

/**
 * Accepts a string and converts it to uuid
 */
@EqualsAndHashCode
@CompileStatic
class StringWrapper {
    private String value

    StringWrapper(String value) throws UuidParsingException {
        this.value = value
    }

    String getValue() {
        return value
    }
}
