@file:Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")

package com.nalepa.mateusz.database.spatial.infrastructure

import com.nalepa.mateusz.database.spatial.domain.User
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.postgis.PGbox2d
import org.postgis.Point
import org.springframework.stereotype.Repository

interface CrudRepository<T, K> {
    fun createTable()
    fun create(m: T): T
    fun findAll(): Iterable<T>
    fun deleteAll(): Int
    fun findByBoundingBox(box: PGbox2d): Iterable<T>
    fun updateLocation(userName: K, location: Point)
}

interface UserRepository : CrudRepository<User, String>


//@Repository
//@Transactional // Should be at @Service level in real applications
class DefaultUserRepository(val db: Database) : UserRepository {

    override fun createTable() = SchemaUtils.create(Users)

    override fun create(user: User): User {
        Users.insert(toRow(user))
        return user
    }

    override fun updateLocation(userName: String, location: Point): Unit = run {
        location.srid = 4326
        Users.update({ Users.userName eq userName }) {
            it[Users.location] = location
        }
    }

    override fun findAll() =
        Users.selectAll()
            .map { fromRow(it) }

    override fun findByBoundingBox(box: PGbox2d) =
        Users.select { Users.location within box }
            .map { fromRow(it) }

    override fun deleteAll() =
        Users.deleteAll()

    private fun toRow(u: User): Users.(UpdateBuilder<*>) -> Unit = {
        it[userName] = u.userName
        it[firstName] = u.firstName
        it[lastName] = u.lastName
        it[location] = u.location
    }

    private fun fromRow(r: ResultRow) =
        User(
            r[Users.userName],
            r[Users.firstName],
            r[Users.lastName],
            r[Users.location]
        )
}