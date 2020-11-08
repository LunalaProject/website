package com.gabriel.lunala.project.backend.database.tables

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object PlanetTable: IdTable<String>() {

    override val id: Column<EntityID<String>> = varchar("name", 32).entityId()

    var distance = long("distance")
    var security = float("security")
    var owner = long("owner").nullable()
    val galaxy = varchar("galaxy", 16)
    var visited = bool("visited")

}