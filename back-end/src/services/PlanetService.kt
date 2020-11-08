package com.gabriel.lunala.project.backend.services

import com.gabriel.lunala.project.backend.entities.Planet
import com.gabriel.lunala.project.backend.entities.PlanetCreateDTO
import com.gabriel.lunala.project.backend.entities.PlanetUpdateDTO
import com.gabriel.lunala.project.backend.entities.findEntitiesByPage
import com.gabriel.lunala.project.backend.factories.ProceduralPlanetFactory
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class PlanetService(val factory: ProceduralPlanetFactory = ProceduralPlanetFactory()) {

    suspend fun storeWithId(create: PlanetCreateDTO): Planet = newSuspendedTransaction {
        factory.create(create)
    }

    suspend fun updateById(id: String, update: PlanetUpdateDTO) = newSuspendedTransaction {
        findById(id)?.apply {
            distance = update.distance ?: distance
            security = update.security ?: security
            owner = update.owner ?: owner
            galaxy = update.galaxy ?: galaxy
            visited = update.visited ?: visited
        }
    }

    suspend fun findById(id: String): Planet? = newSuspendedTransaction {
        Planet.findById(id)
    }

    suspend fun findPaginated(page: Int) = newSuspendedTransaction {
        findEntitiesByPage(Planet.all(), page)
    }

    suspend fun deleteById(id: String) = newSuspendedTransaction {
        findById(id)?.delete()
    }
}