package com.nalepa.mateusz.database.spatial.api

import com.nalepa.mateusz.database.spatial.domain.LocationDto

class PointDto(
    val type: String,
    val coordinates: Array<Double>,
)

fun LocationDto.toDto(): PointDto {
    return PointDto(
        type = "Point",
        coordinates = arrayOf(xLatitude, yLongitude),
    )
}

fun PointDto.toPoint(): LocationDto {
    return LocationDto(coordinates[0], coordinates[1])
}