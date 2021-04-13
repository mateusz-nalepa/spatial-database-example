package com.nalepa.mateusz.database.spatial.infrastructure

import org.postgis.PGbox2d
import org.postgis.Point

interface CrudRepository<T, K> {
    fun createTable()
    fun create(t: T): T
    fun findAll(): Iterable<T>
    fun deleteAll(): Int
    fun findByBoundingBox(box: PGbox2d): Iterable<T>
    fun updateLocation(userName:K, location: Point)
}