package com.nalepa.mateusz.database.spatial.infrastructure

import com.nalepa.mateusz.database.spatial.domain.LocationDto
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

fun LocationDto.toEntity(): PostGisPoint {
    return PostGisPoint(xLatitude, yLongitude)
}

fun PostGisPoint.toDomain(): LocationDto {
    return LocationDto(x, y)
}

