package com.nalepa.mateusz.database.spatial.helper

import com.nalepa.mateusz.database.spatial.domain.LocationDto
import com.nalepa.mateusz.database.spatial.domain.PointOfInterest
import java.io.File
import java.math.BigDecimal

fun main() {
    CSV().savePOI(emptyList())
    val pois = CSV().readPOI()
    println(pois)
}

class CSV {
    private val SEPARATOR = "|"
    val poisFile = File("pois.txt")

    fun savePOI(pois: List<PointOfInterest>) {
        pois.forEach {
            poisFile.appendText(
                "${it.locationDto.xLatitude}$SEPARATOR${it.locationDto.yLongitude}$SEPARATOR${it.name}\n"
            )
        }
    }

    fun readPOI(): List<PointOfInterest> {
        return poisFile
            .bufferedReader()
            .readLines()
            .map { lineToPOI(it) }
    }

    private fun lineToPOI(line: String): PointOfInterest {
        val split = line.split(SEPARATOR)
        return PointOfInterest(
            locationDto = LocationDto(
                xLatitude = BigDecimal(split[0]).toDouble(),
                yLongitude = BigDecimal(split[1]).toDouble()
            ),
            name = split[2]
        )
    }

}