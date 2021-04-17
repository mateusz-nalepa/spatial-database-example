package com.nalepa.mateusz.database.spatial.infrastructure.insertAndRead

import com.nalepa.mateusz.database.spatial.domain.PointOfInterest
import org.postgis.PGbox2d

interface InsertAndReadRepository {
    fun createTable()
    fun insert(poi: PointOfInterest)
    fun findByBoundingBox(box: PGbox2d): List<PointOfInterest>
}

