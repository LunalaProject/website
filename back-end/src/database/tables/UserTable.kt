package com.gabriel.lunala.project.backend.database.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object UserTable: LongIdTable() {

    val coins = long("coins")
    val planet = varchar("planet", 32)

}