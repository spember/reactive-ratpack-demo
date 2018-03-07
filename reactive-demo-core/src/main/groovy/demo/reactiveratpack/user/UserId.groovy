package demo.reactiveratpack.user

import com.thirdchannel.common.StringWrapper
import com.thirdchannel.validation.ValidationException
import demo.reactiveratpack.domain.EntityStreamIdentifier
import groovy.transform.CompileStatic


@CompileStatic
class UserId extends StringWrapper implements EntityStreamIdentifier {
    UserId(final String value) throws ValidationException {
        super(value)
    }
}
