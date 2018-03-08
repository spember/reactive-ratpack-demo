import demo.reativeratpack.modules.todo.TodoApiUrlMappings
import demo.reativeratpack.modules.todo.TodoModule
import ratpack.func.Action
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
        //.onError(Action.throwException()).yaml("config.yaml")
        //.onError(Action.noop()).yaml("/../../config-local.yaml")
                .env() // override local params with incoming Environment params
                .sysProps()

    }

    bindings {
        initialize()
        module HandlebarsModule
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
        }
    }
}
