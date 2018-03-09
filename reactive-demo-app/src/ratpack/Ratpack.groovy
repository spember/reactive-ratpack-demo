import demo.reactiveratpack.modules.todo.TodoApiUrlMappings
import demo.reactiveratpack.modules.todo.TodoModule
import demo.reactiveratpack.modules.websocket.WebSocketModule
import demo.reactiveratpack.modules.websocket.WebsocketApiMappings
import ratpack.handlebars.HandlebarsModule
import ratpack.server.BaseDir

import static ratpack.groovy.Groovy.ratpack
import static ratpack.handlebars.Template.handlebarsTemplate
import static ratpack.rx.RxRatpack.initialize

ratpack {
    serverConfig { config ->
        port(5055)
        config
            .development(true)
            .baseDir(BaseDir.find())
            .env() // override local params with incoming Environment params
            .sysProps()

    }

    bindings {
        initialize()
        module HandlebarsModule
        module WebSocketModule
        module TodoModule

    }

    handlers {
        files { it.dir("public") }

        get {
            render handlebarsTemplate("index.html")
        }

        prefix("api") {
            prefix("todo") {
                all chain(registry.get(TodoApiUrlMappings))
            }
            prefix("events") {
                all chain(registry.get(WebsocketApiMappings))
            }
        }
    }
}
