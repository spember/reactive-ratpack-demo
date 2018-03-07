# Reactive Ratpack Demo
This is a companion for a presentation on creating a service that is Reactive at all levels of the stack.

###Technology:
* Ratpack - http, NIO
* RabbitMQ - async messaging
* rxJava/rxJs -async processing
* React.JS - frontend

###Todos:

Domains:
* User Domain
* Todo List
* Todo Item

####Base App
Functionality:
* User view. Create user
* create todo lists
* add and modify list items
* mark items as complete


####Admin App:
* listens for rabbitmq messages
* contains just a dashboard for listening to events
* 'clear all' command?
