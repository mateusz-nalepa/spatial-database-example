package com.nalepa.mateusz.database.spatial.helper

import com.nalepa.mateusz.database.spatial.domain.LocationDto
import com.nalepa.mateusz.database.spatial.domain.PointOfInterest
import org.apache.commons.lang3.RandomStringUtils
import org.apache.commons.lang3.RandomUtils
import java.math.BigDecimal




const val STRING_LENGTH = 10


fun main() {
    PointOfInterestGenerator().generateXPoi()
}

class PointOfInterestGenerator {
//    private val numberOfPOI = 1_000_000L
    private val numberOfPOI = 100L


    fun generateXPoi() {
        val pois = (0 until numberOfPOI)
            .map { generateSinglePOI() }
            .toList()
            .also { CSV().savePOI(it) }

        println("Number of pois: ${pois.size}")
    }

    private fun generateSinglePOI(): PointOfInterest {
        return PointOfInterest(
            locationDto = LocationDto(
                xLatitude = generateX(),
                yLongitude = generateY()
            ),
            name = RandomStringUtils.randomAlphanumeric(STRING_LENGTH)
        )
    }

    //    -90 do +90
    private fun generateX() = generateDegreeWith6Numbers(90)


    //    -180 do +180
    private fun generateY() = generateDegreeWith6Numbers(180)


    private fun generateDegreeWith6Numbers(degreeRange: Int) =
        BigDecimal("${generateDegree(degreeRange)}.${RandomUtils.nextInt(0, 1_000_000)}").toDouble()


    private fun generateDegree(degreeRange: Int): Int {
        val degree = RandomUtils.nextInt(0, degreeRange + 1)
        return if (RandomUtils.nextBoolean()) {
            degree
        } else {
            degree * -1
        }
    }

}