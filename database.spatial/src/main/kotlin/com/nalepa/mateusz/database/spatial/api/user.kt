package com.nalepa.mateusz.database.spatial.api


import com.nalepa.mateusz.database.spatial.domain.User

class UserDto(
    val userName: String,
    val firstName: String,
    val lastName: String,
    val location: PointDto? = null
)


fun User.toDto(): UserDto {
    return UserDto(
        userName = userName,
        firstName = firstName,
        lastName = lastName,
        location = location?.toDto()
    )
}

fun UserDto.toDomain(): User {
    return User(
        userName = userName,
        firstName = firstName,
        lastName = lastName,
        location = location?.toPoint()
    )
}


