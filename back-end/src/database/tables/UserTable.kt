package com.gabriel.lunala.project.backend.database.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object UserTable: LongIdTable() {

    val ship = integer("ship")
    val coins = long("coins")
    val equipment = integer("equipment")
    val crew = integer("crew")
    val planet = varchar("planet", 32)
    val galaxy = varchar("galaxy", 32)
    val premium = integer("premium")

}