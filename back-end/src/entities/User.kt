package com.gabriel.lunala.project.backend.entities

import com.gabriel.lunala.project.backend.database.tables.UserTable
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class User(id: EntityID<Long>): LongEntity(id) {

    companion object: LongEntityClass<User>(UserTable)

    var coins by UserTable.coins
    var planet by UserTable.planet

    fun toResponseDto() = UserResponseDTO(id.value, coins, planet)

}

@Serializable
data class UserCreateDTO(val id: Long, val coins: Long, val planet: String)

@Serializable
data class UserUpdateDTO(val coins: Long?, val planet: String?)

@Serializable
data class UserResponseDTO(val id: Long, val coins: Long, val planet: String)