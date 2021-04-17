package com.nalepa.mateusz.database.spatial.infrastructure.insertAndRead

import com.nalepa.mateusz.database.spatial.domain.LocationDto
import com.nalepa.mateusz.database.spatial.domain.PointOfInterest
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.postgis.PGbox2d
import org.springframework.transaction.annotation.Transactional

object XYPOITable : Table() {
    val xLat = double("xLat").index()
    val yLon = double("xLon").index()
    val name = text("name")
}

@Transactional
class XYRepository : InsertAndReadRepository {
    override fun createTable() = SchemaUtils.create(XYPOITable)

    override fun insert(poi: PointOfInterest) {
        XYPOITable.insert(toRow(poi))
    }

    private fun toRow(pointOfInterest: PointOfInterest): XYPOITable.(UpdateBuilder<*>) -> Unit = {
        it[name] = pointOfInterest.name
        it[xLat] = pointOfInterest.locationDto.xLatitude
        it[yLon] = pointOfInterest.locationDto.yLongitude
    }

    override fun findByBoundingBox(box: PGbox2d): List<PointOfInterest> {
        return emptyList()
        //        return XYPOITable
//            .select { XYPOITable.location within box }
//            .map { it.toPointOfInterest() }
    }

    private fun ResultRow.toPointOfInterest(): PointOfInterest {
        return PointOfInterest(
            name = this[XYPOITable.name],
            locationDto = LocationDto(
                xLatitude = this[XYPOITable.xLat],
                yLongitude = this[XYPOITable.yLon]
            )
        )
    }
}