package com.gabriel.lunala.project.backend.entities

import com.gabriel.lunala.project.backend.database.tables.GalaxyTable
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Galaxy(id: EntityID<String>): Entity<String>(id) {

    companion object: EntityClass<String, Galaxy>(GalaxyTable)

    val index by GalaxyTable.index

    fun toResponseDto() = GalaxyResponseDto(id.value, index)

}

@Serializable
data class GalaxyResponseDto(
    val name: String,
    val index: Int
)