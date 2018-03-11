package demo.reactiveratpack.todo

import com.fasterxml.jackson.annotation.JsonProperty
import com.thirdchannel.common.StringWrapper
import com.thirdchannel.validation.ValidationException
import demo.reactiveratpack.domain.EntityStreamIdentifier
import groovy.transform.CompileStatic


@CompileStatic
class ListId extends StringWrapper implements EntityStreamIdentifier {
    ListId(@JsonProperty("value") final String value) throws ValidationException {
        super(value)
    }
}
