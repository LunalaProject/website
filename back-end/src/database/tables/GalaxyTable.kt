package com.gabriel.lunala.project.backend.database.tables

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object GalaxyTable: IdTable<String>() {

    override val id: Column<EntityID<String>> = varchar("name", 16).entityId()

    val index = integer("index")

}