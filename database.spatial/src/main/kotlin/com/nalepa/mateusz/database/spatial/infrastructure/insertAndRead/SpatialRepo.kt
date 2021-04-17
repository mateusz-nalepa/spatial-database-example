package com.nalepa.mateusz.database.spatial.infrastructure.insertAndRead

import com.nalepa.mateusz.database.spatial.domain.PointOfInterest
import com.nalepa.mateusz.database.spatial.infrastructure.point
import com.nalepa.mateusz.database.spatial.infrastructure.toDomain
import com.nalepa.mateusz.database.spatial.infrastructure.toEntity
import com.nalepa.mateusz.database.spatial.infrastructure.within
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.postgis.PGbox2d
import org.springframework.transaction.annotation.Transactional

object SpatialPOITable : Table() {
    val location = point("location")//.index()
    val name = text("name")
}

@Transactional
class SpatialRepository : InsertAndReadRepository {
    override fun createTable() = SchemaUtils.create(SpatialPOITable)

    override fun insert(poi: PointOfInterest) {
        SpatialPOITable.insert(toRow(poi))
    }

    private fun toRow(pointOfInterest: PointOfInterest): SpatialPOITable.(UpdateBuilder<*>) -> Unit = {
        it[name] = pointOfInterest.name
        it[location] = pointOfInterest.locationDto.toEntity()
    }

    override fun findByBoundingBox(box: PGbox2d): List<PointOfInterest> {
        return SpatialPOITable
            .select { SpatialPOITable.location within box }
            .map { it.toPointOfInterest() }
    }

    private fun ResultRow.toPointOfInterest(): PointOfInterest {
        return PointOfInterest(
            name = this[SpatialPOITable.name],
            locationDto = this[SpatialPOITable.location].toDomain(),
        )
    }
}