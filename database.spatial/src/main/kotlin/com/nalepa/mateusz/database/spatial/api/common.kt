package com.nalepa.mateusz.database.spatial.api

import com.nalepa.mateusz.database.spatial.domain.Point

class PointDto(
    val type: String,
    val coordinates: Array<Double>,
)

fun Point.toDto(): PointDto {
    return PointDto(
        type = "Point",
        coordinates = arrayOf(x, y),
    )
}

fun PointDto.toPoint(): Point {
    return Point(coordinates[0], coordinates[1])
}