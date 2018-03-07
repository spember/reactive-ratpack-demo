package demo.reactiveratpack.todo

import com.thirdchannel.common.StringWrapper
import com.thirdchannel.validation.ValidationException
import demo.reactiveratpack.domain.EntityStreamIdentifier
import groovy.transform.CompileStatic


@CompileStatic
class ListId extends StringWrapper implements EntityStreamIdentifier {
    ListId(final String value) throws ValidationException {
        super(value)
    }
}
