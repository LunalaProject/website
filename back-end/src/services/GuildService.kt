package com.gabriel.lunala.project.backend.services

import com.gabriel.lunala.project.backend.entities.Guild
import com.gabriel.lunala.project.backend.entities.GuildCreateDTO
import com.gabriel.lunala.project.backend.entities.GuildUpdateDTO
import com.gabriel.lunala.project.backend.entities.findEntitiesByPage
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class GuildService {

    suspend fun storeWithId(create: GuildCreateDTO): Guild = newSuspendedTransaction {
        Guild.findById(create.id) ?: Guild.new(create.id) {
            locale = create.locale
            partner = create.partner
        }
    }

    suspend fun updateById(id: Long, update: GuildUpdateDTO) = newSuspendedTransaction {
        findById(id)?.apply {
            locale = update.locale ?: locale
            partner = update.partner ?: partner
        }
    }

    suspend fun findById(id: Long): Guild? = newSuspendedTransaction {
        Guild.findById(id)
    }

    suspend fun findPaginated(page: Int) = newSuspendedTransaction {
        findEntitiesByPage(Guild.all(), page)
    }

    suspend fun deleteById(id: Long) = newSuspendedTransaction {
        findById(id)?.delete()
    }
}