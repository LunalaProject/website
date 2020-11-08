@file:OptIn(KtorExperimentalAPI::class, KtorExperimentalLocationsAPI::class)

package com.gabriel.lunala.project.backend.routes

import com.gabriel.lunala.project.backend.entities.map
import com.gabriel.lunala.project.backend.services.UserService
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

@Location("/api/users")
class Users {

    @Location("/")
    data class Paginated(val page: Int = 0, val users: Users)

    @Location("/{id}")
    data class Id(val id: Long, val users: Users)

}