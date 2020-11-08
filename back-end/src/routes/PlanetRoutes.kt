@file:OptIn(KtorExperimentalAPI::class, KtorExperimentalLocationsAPI::class)

package com.gabriel.lunala.project.backend.routes

import com.gabriel.lunala.project.backend.entities.map
import com.gabriel.lunala.project.backend.services.PlanetService
import com.gabriel.lunala.project.backend.utils.PAGES_HEADER
import com.gabriel.lunala.project.backend.utils.authenticated
import io.ktor.application.*
import io.ktor.config.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*

@OptIn(KtorExperimentalAPI::class, KtorExperimentalLocationsAPI::class)
fun Route.planetRoutes(config: ApplicationConfig, planetService: PlanetService) = authenticated(config) {
    put<Planets.Id> { (id) ->
        when(val planet = planetService.updateById(id, call.receive())) {
            null -> call.respond(HttpStatusCode.NotFound)
            else -> call.respond(planet.toResponseDto())
        }
    }
    post<Planets> {
        call.respond(HttpStatusCode.Created, planetService.storeWithId(call.receive()).toResponseDto())
    }
    get<Planets.Id> { (id) ->
        when (val planet = planetService.findById(id)) {
            null -> call.respond(HttpStatusCode.NotFound)
            else -> call.respond(planet.toResponseDto())
        }
    }
    get<Planets.Paginated> { (page) ->
        val (pages, items) = planetService.findPaginated(page).map { planet ->
            planet.toResponseDto()
        }

        call.response.headers.append(PAGES_HEADER, pages.toString())
        call.respond(items)
    }
    delete<Planets.Id> { (id) ->
        planetService.deleteById(id)
        call.respond(HttpStatusCode.NoContent)
    }
}

@Location("/api/planets")
class Planets {

    @Location("/")
    data class Paginated(val page: Int = 0, val planets: Planets)

    @Location("/{id}")
    data class Id(val id: String, val planets: Planets)

}