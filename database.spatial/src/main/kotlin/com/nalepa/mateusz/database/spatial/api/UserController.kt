package com.nalepa.mateusz.database.spatial.api

import com.nalepa.mateusz.database.spatial.domain.User
import com.nalepa.mateusz.database.spatial.infrastructure.UserRepository
import org.postgis.PGbox2d
import org.postgis.Point
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(val repo: UserRepository) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody u: User) {
        repo.create(u)
    }

    @GetMapping
    fun list() =
        repo.findAll()

    @GetMapping("/bbox/{xMin},{yMin},{xMax},{yMax}")
    fun findByBoundingBox(
        @PathVariable xMin: Double,
        @PathVariable yMin: Double,
        @PathVariable xMax: Double,
        @PathVariable yMax: Double
    ) =
        repo.findByBoundingBox(
            PGbox2d(Point(xMin, yMin), Point(xMax, yMax))
        )

    @PutMapping("/{userName}/location/{x},{y}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateLocation(
        @PathVariable userName: String,
        @PathVariable x: Double,
        @PathVariable y: Double
    ) =
        repo.updateLocation(userName, Point(x, y))
}