package com.gabriel.lunala.project.backend.entities

import com.gabriel.lunala.project.backend.database.tables.PlanetTable
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Planet(id: EntityID<String>): Entity<String>(id) {

    companion object: EntityClass<String, Planet>(PlanetTable)

    var distance: Long by PlanetTable.distance
    var security: Float by PlanetTable.security
    var owner: Long? by PlanetTable.owner
    var galaxy: String by PlanetTable.galaxy
    var visited: Boolean by PlanetTable.visited

    fun toResponseDto() = PlanetResponseDTO(id.value, distance, security, owner, galaxy, visited)

}

@Serializable
data class PlanetCreateDTO(
    val name: String? = null,
    val distance: Long? = null,
    val security: Float? = null,
    val owner: Long? = null,
    val galaxy: String? = null,
    val visited: Boolean = false
)

@Serializable
data class PlanetUpdateDTO(
    val distance: Long? = null,
    val security: Float? = null,
    val owner: Long? = null,
    val galaxy: String? = null,
    val visited: Boolean? = null
)

@Serializable
data class PlanetResponseDTO(
    val name: String,
    val distance: Long,
    val security: Float,
    val owner: Long?,
    val galaxy: String,
    val visited: Boolean,
)