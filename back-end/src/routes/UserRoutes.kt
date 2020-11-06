@file:OptIn(KtorExperimentalAPI::class, KtorExperimentalLocationsAPI::class)

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
fun Route.userRoutes(config: ApplicationConfig, userService: UserService) = authenticated(config) {
    put<Users.Id> { (id) ->
        when(val user = userService.updateById(id, call.receive())) {
            null -> call.respond(HttpStatusCode.NotFound)
            else -> call.respond(user.toResponseDto())
        }
    }
    post<Users> {
        call.respond(HttpStatusCode.Created, userService.storeWithId(call.receive()).toResponseDto())
    }
    get<Users.Id> { (id) ->
        when (val user = userService.findById(id)) {
            null -> call.respond(HttpStatusCode.NotFound)
            else -> call.respond(user.toResponseDto())
        }
    }
    get<Users.Paginated> { (page) ->
        val (pages, items) = userService.findPaginated(page).map { user ->
            user.toResponseDto()
        }

        call.response.headers.append(PAGES_HEADER, pages.toString())
        call.respond(items)
    }
    delete<Users.Id> { (id) ->
        userService.deleteById(id)
        call.respond(HttpStatusCode.NoContent)
    }
}

@OptIn(KtorExperimentalAPI::class)
fun Route.authenticated(config: ApplicationConfig, route: Route.() -> Unit): Route {
    intercept(ApplicationCallPipeline.Features) {
        validate(config, call.request.authorization().orEmpty())
    }
    return apply(route)
}

@OptIn(KtorExperimentalAPI::class)
private suspend fun PipelineContext<Unit, ApplicationCall>.validate(config: ApplicationConfig, value: String) = if (config.property("key").getList().contains(value).not())
    call.respond(HttpStatusCode.Unauthorized) else Unit

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