package com.gabriel.lunala.project.backend.factories

import com.gabriel.lunala.project.backend.entities.Planet
import com.gabriel.lunala.project.backend.entities.PlanetCreateDTO
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ProceduralPlanetFactory {

    suspend fun create(base: PlanetCreateDTO): Planet = newSuspendedTransaction {
        val procedural = procedurally(base)
        base.name?.let { Planet.findById(it) } ?: Planet.new(procedural.name!!) {
            distance = base.distance ?: procedural.distance!!
            security = base.security ?: procedural.security!!
            owner = base.owner
            galaxy = base.galaxy ?: procedural.galaxy!!
            visited = base.visited
        }
    }

    @Deprecated("TODO")
    private fun procedurally(base: PlanetCreateDTO): PlanetCreateDTO = PlanetCreateDTO(
        "Earth",
        0,
        100f,
        null,
        "Milky Way",
        false
    )
}