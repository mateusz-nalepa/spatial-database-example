package com.nalepa.mateusz.database.spatial.api

import com.nalepa.mateusz.database.spatial.domain.Message


class MessageDto(
    val content: String,
    val author: String,
    val location: PointDto? = null,
    val id: Int? = null
)

fun MessageDto.toDomain(): Message {
    return Message(
        content = content,
        author = author,
        location = location?.toPoint(),
        id = id
    )
}

fun Message.toDto(): MessageDto {
    return MessageDto(
        content = content,
        author = author,
        location = location?.toDto(),
        id = id
    )
}