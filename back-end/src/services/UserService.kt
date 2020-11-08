package com.gabriel.lunala.project.backend.services

import com.gabriel.lunala.project.backend.entities.User
import com.gabriel.lunala.project.backend.entities.UserCreateDTO
import com.gabriel.lunala.project.backend.entities.UserUpdateDTO
import com.gabriel.lunala.project.backend.entities.findEntitiesByPage
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class UserService {

    suspend fun storeWithId(create: UserCreateDTO): User = newSuspendedTransaction {
        User.findById(create.id) ?: User.new(create.id) {
            ship = create.ship
            coins = create.coins
            equipment = create.equipment
            crew = create.crew
            planet = create.planet
            galaxy = create.galaxy
            premium = create.premium
        }
    }

    suspend fun updateById(id: Long, update: UserUpdateDTO) = newSuspendedTransaction {
        findById(id)?.apply {
            ship = update.ship ?: ship
            coins = update.coins ?: coins
            equipment = update.equipment ?: equipment
            crew = update.crew ?: crew
            planet = update.planet ?: planet
            galaxy = update.galaxy ?: galaxy
            premium = update.premium ?: premium
        }
    }

    suspend fun findById(id: Long): User? = newSuspendedTransaction {
        User.findById(id)
    }

    suspend fun findPaginated(page: Int) = newSuspendedTransaction {
        findEntitiesByPage(User.all(), page)
    }

    suspend fun deleteById(id: Long) = newSuspendedTransaction {
        findById(id)?.delete()
    }
}