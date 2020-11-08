package com.gabriel.lunala.project.backend.database.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object GuildTable: LongIdTable() {

    val partner = bool("partner")

}