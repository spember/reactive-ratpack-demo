package demo.reactiveratpack.user

import com.fasterxml.jackson.annotation.JsonProperty
import com.thirdchannel.common.StringWrapper
import com.thirdchannel.validation.ValidationException
import demo.reactiveratpack.domain.EntityStreamIdentifier
import groovy.transform.CompileStatic


@CompileStatic
class UserId extends StringWrapper implements EntityStreamIdentifier {
    UserId(@JsonProperty("value") final String value) throws ValidationException {
        super(value)
    }
}
