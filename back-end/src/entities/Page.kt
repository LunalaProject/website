package com.gabriel.lunala.project.backend.entities

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.SizedIterable
import kotlin.math.ceil

const val DEFAULT_PAGE_SIZE = 100

@Serializable
data class Page<T>(
    val pages: Int,
    val items: List<T>,
)

fun <T> Iterable<T>.asPage(pages: Number): Page<T> =
    Page(pages.toInt(), toList())

inline fun <T, R> Page<T>.map(func: (T) -> R): Page<R> =
    Page(pages, items.map(func))

fun <T> SizedIterable<T>.paginate(page: Int, pageSize: Int) =
    limit(pageSize, ((page - 1) * pageSize).toLong())

fun <T> findEntitiesByPage(entities: SizedIterable<T>, _page: Int): Page<T> {
    val page = if (_page <= 0) 1 else _page

    return entities.paginate(page, DEFAULT_PAGE_SIZE).asPage(ceil((entities.count() / DEFAULT_PAGE_SIZE + 1).toDouble()))
}