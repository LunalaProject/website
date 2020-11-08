package com.gabriel.lunala.project.backend.entities

import com.gabriel.lunala.project.backend.database.tables.UserTable
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class User(id: EntityID<Long>): LongEntity(id) {

    companion object: LongEntityClass<User>(UserTable)

    var ship: Int by UserTable.ship
    var coins: Long by UserTable.coins
    var equipment: Int by UserTable.equipment
    var crew: Int by UserTable.crew
    var planet: String by UserTable.planet
    var galaxy: String by UserTable.galaxy
    var premium: Int by UserTable.premium

    fun toResponseDto() = UserResponseDTO(id.value, ship, coins, equipment, crew, planet, galaxy, premium)

}

@Serializable
data class UserCreateDTO(
    val id: Long,
    val ship: Int,
    val coins: Long,
    val equipment: Int,
    val crew: Int,
    val planet: String,
    val galaxy: String,
    val premium: Int
)

@Serializable
data class UserUpdateDTO(
    val ship: Int? = null,
    val coins: Long? = null,
    val equipment: Int? = null,
    val crew: Int? = null,
    val planet: String? = null,
    val galaxy: String? = null,
    val premium: Int? = null
)

@Serializable
data class UserResponseDTO(
    val id: Long,
    val ship: Int,
    val coins: Long,
    val equipment: Int,
    val crew: Int,
    val planet: String,
    val galaxy: String,
    val premium: Int
)