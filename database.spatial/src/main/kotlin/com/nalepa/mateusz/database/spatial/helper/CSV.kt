package com.nalepa.mateusz.database.spatial.helper

import com.nalepa.mateusz.database.spatial.domain.LocationDto
import com.nalepa.mateusz.database.spatial.domain.PointOfInterest
import com.nalepa.mateusz.database.spatial.domain.SearchBox
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
    val searchBoxesFile = File("searchBoxes.txt")

    fun savePOI(pois: List<PointOfInterest>) {
        val writer = poisFile.bufferedWriter()
        pois.forEach {
            writer
                .write("${it.locationDto.xLatitude}$SEPARATOR${it.locationDto.yLongitude}$SEPARATOR${it.name}\n")
        }
        writer.flush()
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

    fun saveSearchBoxes(searchBoxes: List<SearchBox>) {
        val writer = searchBoxesFile.bufferedWriter()
        searchBoxes.forEach {
            writer
                .write("${it.llx}$SEPARATOR${it.lly}$SEPARATOR${it.urX}$SEPARATOR${it.urY}\n")
        }
        writer.flush()
    }

    fun readSearchBoxes(): List<SearchBox> {
        return searchBoxesFile
            .bufferedReader()
            .readLines()
            .map { lineToSearchBox(it) }
    }

    private fun lineToSearchBox(line: String): SearchBox {
        val split = line.split(SEPARATOR)
        return SearchBox(
            llx = BigDecimal(split[0]).toDouble(),
            lly = BigDecimal(split[1]).toDouble(),
            urX = BigDecimal(split[2]).toDouble(),
            urY = BigDecimal(split[3]).toDouble()
        )
    }

}