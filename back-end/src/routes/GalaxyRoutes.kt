@file:OptIn(KtorExperimentalAPI::class, KtorExperimentalLocationsAPI::class)

package com.gabriel.lunala.project.backend.routes

import com.gabriel.lunala.project.backend.services.GalaxyService
import com.gabriel.lunala.project.backend.utils.authenticated
import io.ktor.application.*
import io.ktor.config.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*

@OptIn(KtorExperimentalAPI::class, KtorExperimentalLocationsAPI::class)
fun Route.galaxyRoutes(config: ApplicationConfig, galaxyService: GalaxyService) = authenticated(config) {
    put<Galaxies.Id> { (_) ->
        call.respond(HttpStatusCode.MethodNotAllowed)
    }
    post<Galaxies> {
        call.respond(HttpStatusCode.MethodNotAllowed)
    }
    get<Galaxies.Id> { (id) ->
        when (val galaxy = galaxyService.findById(id)) {
            null -> call.respond(HttpStatusCode.NotFound)
            else -> call.respond(galaxy.toResponseDto())
        }
    }
    delete<Galaxies.Id> { (_) ->
        call.respond(HttpStatusCode.MethodNotAllowed)
    }
}

@Location("/api/galaxies")
class Galaxies {

    @Location("/{id}")
    data class Id(val id: String, val galaxies: Galaxies)

}