@file:OptIn(KtorExperimentalAPI::class, KtorExperimentalLocationsAPI::class)

package com.gabriel.lunala.project.backend.routes

import com.gabriel.lunala.project.backend.entities.map
import com.gabriel.lunala.project.backend.services.GuildService
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
fun Route.guildRoutes(config: ApplicationConfig, guildService: GuildService) = authenticated(config) {
    put<Guilds.Id> { (id) ->
        when(val guild = guildService.updateById(id, call.receive())) {
            null -> call.respond(HttpStatusCode.NotFound)
            else -> call.respond(guild.toResponseDto())
        }
    }
    post<Guilds> {
        call.respond(HttpStatusCode.Created, guildService.storeWithId(call.receive()).toResponseDto())
    }
    get<Guilds.Id> { (id) ->
        when (val guild = guildService.findById(id)) {
            null -> call.respond(HttpStatusCode.NotFound)
            else -> call.respond(guild.toResponseDto())
        }
    }
    get<Guilds.Paginated> { (page) ->
        val (pages, items) = guildService.findPaginated(page).map { guild ->
            guild.toResponseDto()
        }

        call.response.headers.append(PAGES_HEADER, pages.toString())
        call.respond(items)
    }
    delete<Guilds.Id> { (id) ->
        guildService.deleteById(id)
        call.respond(HttpStatusCode.NoContent)
    }
}

@Location("/api/guilds")
class Guilds {

    @Location("/")
    data class Paginated(val page: Int = 0, val guilds: Guilds)

    @Location("/{id}")
    data class Id(val id: Long, val guilds: Guilds)

}