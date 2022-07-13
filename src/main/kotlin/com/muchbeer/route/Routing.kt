package com.muchbeer.route

import com.muchbeer.db.DatabaseFactory
import com.muchbeer.model.School
import com.muchbeer.model.USSDModel
import com.muchbeer.repository.DataRepository
import com.muchbeer.repository.DataRepositoryImpl
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import org.slf4j.event.Level


fun Application.configureMonitoring()  {
    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }
}


