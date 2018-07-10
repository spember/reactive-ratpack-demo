package demo.reactiveratpack.user

import com.fasterxml.jackson.annotation.JsonProperty
import demo.reactiveratpack.domain.EntityStreamIdentifier
import demo.reactiveratpack.domain.StringWrapper
import demo.reactiveratpack.exceptions.UuidParsingException
import groovy.transform.CompileStatic


@CompileStatic
class UserId extends StringWrapper implements EntityStreamIdentifier {
    UserId(@JsonProperty("value") final String value) throws UuidParsingException {
        super(value)
    }
}
