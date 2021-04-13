package com.nalepa.mateusz.database.spatial.infrastructure


import com.nalepa.mateusz.database.spatial.domain.User
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.postgis.PGbox2d
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

interface UserRepository : CrudRepository<User, String>

@Repository
@Transactional // Should be at @Service level in real applications
class DefaultUserRepository : UserRepository {

    override fun createTable() = SchemaUtils.create(Users)

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun create(user: User): User {
        Users.insert(toRow(user))
        return user
    }

    override fun updateLocation(userName: String, location: PostGisPoint) {
        location.srid = 4326
        Users.update({ Users.userName eq userName }) {
            it[Users.location] = location
        }
    }

    override fun findAll() =
        Users
            .selectAll()
            .map { it.toUser() }

    override fun findByBoundingBox(box: PGbox2d) =
        Users
            .select { Users.location within box }
            .map { it.toUser() }

    override fun deleteAll() = Users.deleteAll()

    private fun toRow(u: User): Users.(UpdateBuilder<*>) -> Unit = {
        it[userName] = u.userName
        it[firstName] = u.firstName
        it[lastName] = u.lastName
        it[location] = u.location?.toEntity()
    }

    private fun ResultRow.toUser() =
        User(
            userName = this[Users.userName],
            firstName = this[Users.firstName],
            lastName = this[Users.lastName],
            location = this[Users.location]?.toDomain()
        )
}