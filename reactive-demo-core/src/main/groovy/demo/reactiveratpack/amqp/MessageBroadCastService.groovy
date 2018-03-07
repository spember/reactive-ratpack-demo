package demo.reactiveratpack.amqp

import demo.reactiveratpack.domain.Event


interface MessageBroadCastService {

    void transmit(List<Event> events)
}
