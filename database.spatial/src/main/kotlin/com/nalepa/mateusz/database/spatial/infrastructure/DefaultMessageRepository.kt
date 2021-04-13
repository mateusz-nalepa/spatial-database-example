package com.nalepa.mateusz.database.spatial.infrastructure

import com.nalepa.mateusz.database.spatial.domain.Message
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.postgis.PGbox2d
import org.postgis.Point
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

interface MessageRepository : CrudRepository<Message, Int>

@Repository
@Transactional // Should be at @Service level in real applications
class DefaultMessageRepository : MessageRepository {

    override fun createTable() = SchemaUtils.create(Messages)

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun create(message: Message): Message =
        message.copy(
            id = Messages.insert(toRow(message))[Messages.id]
        )

    override fun findAll() = Messages.selectAll().map { it.toMessage() }

    override fun findByBoundingBox(box: PGbox2d) =
        Messages
            .select { Messages.location within box }
            .map { it.toMessage() }

    override fun updateLocation(userName: Int, location: Point) {
        location.srid = 4326
        Messages.update({ Messages.id eq userName }) {
            it[Messages.location] = location
        }
    }

    override fun deleteAll() = Messages.deleteAll()

    private fun toRow(message: Message): Messages.(UpdateBuilder<*>) -> Unit = {
        if (message.id != null) {
            it[id] = message.id
        }
        it[content] = message.content
        it[author] = message.author
        it[location] = message.location?.toEntity()
    }

    private fun ResultRow.toMessage() =
        Message(
            content = this[Messages.content],
            author = this[Messages.author],
            location = this[Messages.location]?.toDomain(),
            id = this[Messages.id]
        )
}