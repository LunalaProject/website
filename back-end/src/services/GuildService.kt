package com.gabriel.lunala.project.backend.services

import com.gabriel.lunala.project.backend.entities.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.math.ceil

class GuildService {

    suspend fun storeWithId(create: GuildCreateDTO): Guild = newSuspendedTransaction {
        Guild.findById(create.id) ?: Guild.new(create.id) {
            partner = create.partner
        }
    }

    suspend fun updateById(id: Long, update: GuildUpdateDTO) = newSuspendedTransaction {
        findById(id)?.apply {
            partner = update.partner ?: partner
        }
    }

    suspend fun findById(id: Long): Guild? = newSuspendedTransaction {
        Guild.findById(id)
    }

    suspend fun findPaginated(page: Int) = newSuspendedTransaction {
        val page = if (page <= 0) 1 else page
        val allGuilds = Guild.all()

        allGuilds.paginate(page, DEFAULT_PAGE_SIZE).asPage(ceil((allGuilds.count() / DEFAULT_PAGE_SIZE + 1).toDouble()))
    }

    suspend fun deleteById(id: Long) = newSuspendedTransaction {
        findById(id)?.delete()
    }

}