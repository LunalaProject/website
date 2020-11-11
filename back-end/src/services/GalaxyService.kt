package com.gabriel.lunala.project.backend.services

import com.gabriel.lunala.project.backend.entities.Galaxy
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class GalaxyService {

    suspend fun findById(id: String): Galaxy? = newSuspendedTransaction {
        Galaxy.findById(id)
    }
}