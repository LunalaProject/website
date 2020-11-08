package com.gabriel.lunala.project.backend.services

import com.gabriel.lunala.project.backend.entities.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.math.ceil

class UserService {

    suspend fun storeWithId(create: UserCreateDTO): User = newSuspendedTransaction {
        User.findById(create.id) ?: User.new(create.id) {
            coins = create.coins
            planet = create.planet
        }
    }

    suspend fun updateById(id: Long, update: UserUpdateDTO) = newSuspendedTransaction {
        findById(id)?.apply {
            coins = update.coins ?: coins
            planet = update.planet ?: planet
        }
    }

    suspend fun findById(id: Long): User? = newSuspendedTransaction {
        User.findById(id)
    }

    suspend fun findPaginated(page: Int) = newSuspendedTransaction {
        val page = if (page <= 0) 1 else page
        val allUsers = User.all()

        allUsers.paginate(page, DEFAULT_PAGE_SIZE).asPage(ceil((allUsers.count() / DEFAULT_PAGE_SIZE + 1).toDouble()))
    }

    suspend fun deleteById(id: Long) = newSuspendedTransaction {
        findById(id)?.delete()
    }

}