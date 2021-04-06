package com.nalepa.mateusz.database.spatial.domain

import org.postgis.Point

class Message(
        var content: String,
        var author: String,
        var location: Point? = null,
        var id: Int? = null
)

class User(
        var userName: String,
        var firstName: String,
        var lastName: String,
        var location: Point? = null
)