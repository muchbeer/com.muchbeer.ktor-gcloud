package com.muchbeer

import com.muchbeer.db.DatabaseFactory
import com.muchbeer.model.School
import com.muchbeer.model.USSDModel
import com.muchbeer.repository.DataRepository
import com.muchbeer.repository.DataRepositoryImpl
import io.ktor.server.application.*
import com.muchbeer.route.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {

    install(DefaultHeaders) {
        header(HttpHeaders.Accept, "muchbeer")
    }
    configureMonitoring()
    //  val repository : DataRepository by inject()

    val dbConnect = DatabaseFactory.init()

    val repository: DataRepository = DataRepositoryImpl(dbConnect)
    val listTem: List<School> = listOf(
        School(1, "Lubaga", "Shy", "Both"),
        School(1, "Loyola", "Dar", "Both")
    )


    routing {
        get("/") {
            call.respond(mapOf("data" to listTem))
        }

        get("/gadiel") {
            call.respondText("remove header auto")
        }
    }

    routing {

        get("/schools") {
            val retrieveSchool: List<School> = repository.retrieveAllSchool()
            call.respond("$retrieveSchool")
        }


        post("/register") {
        //    call.response.headers.append("Content-Type", "application/json")

            val addSchool = call.receiveOrNull<School>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }


            val response: School = repository.insertSchool(addSchool)

            call.respond(status = HttpStatusCode.OK, response)
        }

        post("/ussd") {
            val addUssd = call.receive<USSDModel>()

            if (repository.findUSSDSessionById(addUssd.sessionId) != null) {
                repository.updateSessionId(addUssd.sessionId, addUssd)
                call.respond(status = HttpStatusCode.OK, message = "Success ")
            } else {
                val response = repository.insertUSSD(addUssd)
                call.respond(status = HttpStatusCode.OK, response)
            }

        }

    }
}


