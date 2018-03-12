package demo.reactiveratpack.todo

import com.fasterxml.jackson.annotation.JsonProperty
import com.thirdchannel.common.StringWrapper
import com.thirdchannel.validation.ValidationException
import demo.reactiveratpack.domain.EntityStreamIdentifier
import groovy.transform.CompileStatic

@CompileStatic
class ItemId extends StringWrapper implements EntityStreamIdentifier {
    ItemId(@JsonProperty("value") final String value) throws ValidationException {
        super(value)
    }
}
