package demo.reactiveratpack.todo

import com.fasterxml.jackson.annotation.JsonProperty

import demo.reactiveratpack.domain.EntityStreamIdentifier
import demo.reactiveratpack.domain.StringWrapper
import groovy.transform.CompileStatic

@CompileStatic
class ItemId extends StringWrapper implements EntityStreamIdentifier {
    ItemId(@JsonProperty("value") final String value) {
        super(value)
    }
}
