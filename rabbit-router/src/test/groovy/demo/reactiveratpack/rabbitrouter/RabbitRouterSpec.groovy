package demo.reactiveratpack.rabbitrouter

import spock.lang.Specification

class RabbitRouterSpec extends Specification {

    def "test basic connectivity" () {
        given:
        int x = 1

        when:
        x += 1

        then:
        x == 2

    }
}
