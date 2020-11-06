@file:OptIn(KtorExperimentalLocationsAPI::class)

package com.gabriel.lunala.project.backend.routes

import com.gabriel.lunala.project.backend.entities.map
import com.gabriel.lunala.project.backend.services.UserService
import io.ktor.application.*
import io.ktor.config.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import io.ktor.util.pipeline.*

const val PAGES_HEADER = "X-Total-Pages"

@OptIn(KtorExperimentalAPI::class, KtorExperimentalLocationsAPI::class)
fun Route.userRoutes(appConfig: ApplicationConfig, userService: UserService) {
    put<Users.Id> { (id) ->
        if (authorizate(appConfig).not())
            return@put call.respond(HttpStatusCode.Unauthorized)

        when(val user = userService.updateById(id, call.receive())) {
            null -> call.respond(HttpStatusCode.NotFound)
            else -> call.respond(user.toResponseDto())
        }
    }

    post<Users> {
        if (authorizate(appConfig).not())
            return@post call.respond(HttpStatusCode.Unauthorized)

        call.respond(HttpStatusCode.Created, userService.storeWithId(call.receive()).toResponseDto())
    }

    get<Users.Id> { (id) ->
        if (authorizate(appConfig).not())
            return@get call.respond(HttpStatusCode.Unauthorized)

        when (val user = userService.findById(id)) {
            null -> call.respond(HttpStatusCode.NotFound)
            else -> call.respond(user.toResponseDto())
        }
    }

    get<Users.Paginated> { (page) ->
        if (authorizate(appConfig).not())
            return@get call.respond(HttpStatusCode.Unauthorized)

        val (pages, items) = userService.findPaginated(page).map { user ->
            user.toResponseDto()
        }

        call.response.headers.append(PAGES_HEADER, pages.toString())
        call.respond(items)
    }

    delete<Users.Id> { (id) ->
        if (authorizate(appConfig).not())
            return@delete call.respond(HttpStatusCode.Unauthorized)

        userService.deleteById(id)
        call.respond(HttpStatusCode.NoContent)
    }

}

@OptIn(KtorExperimentalAPI::class)
fun PipelineContext<Unit, ApplicationCall>.authorizate(config: ApplicationConfig): Boolean =
    config.property("keys").getList().contains(call.request.authorization())

@Location("/users")
class Users {

    @Location("/")
    data class Paginated(val page: Int = 0, val users: Users)

    @Location("/{id}")
    data class Id(val id: Long, val users: Users)

}