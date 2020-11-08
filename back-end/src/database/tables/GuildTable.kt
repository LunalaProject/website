package com.gabriel.lunala.project.backend.database.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object GuildTable: LongIdTable() {

    val locale = varchar("locale", 5)
    val partner = bool("partner")

}