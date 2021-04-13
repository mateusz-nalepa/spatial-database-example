package com.nalepa.mateusz.database.spatial.api

import com.nalepa.mateusz.database.spatial.infrastructure.UserRepository
import org.postgis.PGbox2d
import org.postgis.Point
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(val repository: UserRepository) {

    @PostMapping
    @ResponseStatus(CREATED)
    fun create(@RequestBody u: UserDto) {
        repository.create(u.toDomain())
    }

    @GetMapping
    fun list(): List<UserDto> {
        return repository.findAll().map { it.toDto() }
    }

    @GetMapping("/bbox/{xMin},{yMin},{xMax},{yMax}")
    fun findByBoundingBox(
        @PathVariable xMin: Double, @PathVariable yMin: Double,
        @PathVariable xMax: Double, @PathVariable yMax: Double
    ) = repository.findByBoundingBox(PGbox2d(Point(xMin, yMin), Point(xMax, yMax)))

    @PutMapping("/{userName}/location/{x},{y}")
    @ResponseStatus(NO_CONTENT)
    fun updateLocation(
        @PathVariable userName: String,
        @PathVariable x: Double, @PathVariable y: Double
    ) = repository.updateLocation(userName, Point(x, y))
}