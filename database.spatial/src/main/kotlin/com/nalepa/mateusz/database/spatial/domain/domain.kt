package com.nalepa.mateusz.database.spatial.domain


data class Message(
    val content: String,
    val author: String,
    val location: Point? = null,
    val id: Int? = null
)

data class User(
    val userName: String,
    val firstName: String,
    val lastName: String,
    val location: Point? = null
)

data class Point(
    val x: Double,
    val y: Double
)