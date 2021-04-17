package com.nalepa.mateusz.database.spatial

import com.nalepa.mateusz.database.spatial.domain.PointOfInterest
import com.nalepa.mateusz.database.spatial.helper.CSV
import com.nalepa.mateusz.database.spatial.helper.TimeSaver
import com.nalepa.mateusz.database.spatial.infrastructure.insertAndRead.InsertAndReadRepository
import io.vavr.kotlin.list
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.time.LocalTime
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis


@Component
class DatabaseTest(
    private val repo: InsertAndReadRepository
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        println("${LocalTime.now()}: Starting read points")
        val pois = CSV().readPOI()
        println("${LocalTime.now()}: Read all points")
        repo.createTable()
        var list: io.vavr.collection.List<Long> = list()


        pois
            .chunked(100)
            .forEach {
                list = list.prepend(insertMeasured(it))
            }
        println("${LocalTime.now()}: Added all points")
        val reverse = list.reverse().drop(1)
        println("Number of points: ${reverse.size()}")
        TimeSaver().saveWriteTime(reverse.toJavaList())
    }
    //min Some(3628700)
    //max Some(39872200)

    private fun insertMeasured(poi: PointOfInterest): Long {
        return measureNanoTime { repo.insert(poi) }
    }

    private fun insertMeasured(poi: List<PointOfInterest>): Long {
        return measureTimeMillis {
            poi.forEach { repo.insert(it) }
        }
    }

}