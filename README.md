# Reactive Ratpack Demo
This is a companion for a presentation on creating a service that is Reactive at all levels of the stack.

### Technology:
* Ratpack - http, NIO
* RabbitMQ - async messaging
* rxJava/rxJs -async processing
* React.JS - frontend


### Code Layout
Code is broken up into multiple smaller projects, taking advantage of Gradle's multi project build feature.
This is done to promote separation of concerns (e.g. no Ratpack classes go in __core__ ) and a ports-and-adapters style
(e.g. core specifies interfaces that are implemented by another module).

* `reactive-demo-core` -> base classes, core functionality of the backend
* `reactive-demo-in-memory` -> contains an in-memory cache implementation of core's data repository interfaces. In real code, this
would likely be something like a `-mysql` or `-postgres`
* `reactive-demo-app` -> the 'main' entry point. contains the main executable, Ratpack code, and React frontend code (_src/main/webapp_). 
Responsible for merging the different modules for execution (e.g. using D.I to include the in-memory repository when core classes need an implementation)


### Todos:

Domains:
* User Domain
* Todo List
* Todo Item

#### Base App
Functionality:
* User view. Create user
* create todo lists
* add and modify list items
* mark items as complete


#### Admin App:
* listens for rabbitmq messages
* contains just a dashboard for listening to events
* 'clear all' command?
