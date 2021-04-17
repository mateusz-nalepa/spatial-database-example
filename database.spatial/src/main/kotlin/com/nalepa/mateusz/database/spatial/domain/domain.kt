package com.nalepa.mateusz.database.spatial.domain


data class Message(
    val content: String,
    val author: String,
    val location: LocationDto? = null,
    val id: Int? = null
)

data class User(
    val userName: String,
    val firstName: String,
    val lastName: String,
    val location: LocationDto? = null
)

data class LocationDto(
    // od min -90 zachow do max + 90 na wschod
    val xLatitude: Double,
    // od -180 na poludnie do +180 na polnosc
    val yLongitude: Double
)