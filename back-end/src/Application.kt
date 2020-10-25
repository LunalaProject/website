@file:OptIn(KtorExperimentalAPI::class, KtorExperimentalLocationsAPI::class)

package com.gabriel.lunala.project.backend

import com.gabriel.lunala.project.backend.routes.userRoutes
import com.gabriel.lunala.project.backend.database.DatabaseService
import com.gabriel.lunala.project.backend.services.UserService
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.features.*
import org.slf4j.event.*
import io.ktor.auth.*
import io.ktor.routing.*

import io.ktor.serialization.*
import io.ktor.util.*

fun main(args: Array<String>): Unit = io.ktor.server.jetty.EngineMain.main(args)

@Suppress("unused")
@OptIn(KtorExperimentalAPI::class, KtorExperimentalLocationsAPI::class)
fun Application.module() {
    install(CORS) {
        anyHost()
        allowCredentials = true

        header("Content-Type")
        header(HttpHeaders.Authorization)

        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
    }

    install(Locations)
    install(ContentNegotiation) {
        json()
    }

    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }

    DatabaseService(environment.config.config("database")).let {
        it.connect()
        it.createTables()
    }

    val userService = UserService()

    authentication {

    }

    routing {
        userRoutes(userService)
    }
}

