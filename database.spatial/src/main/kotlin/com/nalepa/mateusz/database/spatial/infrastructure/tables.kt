package com.nalepa.mateusz.database.spatial.infrastructure

import com.nalepa.mateusz.database.spatial.domain.Point
import org.jetbrains.exposed.sql.Table

typealias PostGisPoint = org.postgis.Point


object Messages : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val content = text("content")
    val author = reference("author", Users.userName)
    val location = point("location").nullable()
}

object Users : Table() {
    val userName = text("user_name").primaryKey()
    val firstName = text("first_name")
    val lastName = text("last_name")
    val location = point("location").nullable()
}

fun Point.toEntity(): PostGisPoint {
    return PostGisPoint(x, y)
}

fun PostGisPoint.toDomain(): Point {
    return Point(x, y)
}

