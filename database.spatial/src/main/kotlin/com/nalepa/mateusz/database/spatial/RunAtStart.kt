package com.nalepa.mateusz.database.spatial

import com.nalepa.mateusz.database.spatial.domain.Message
import com.nalepa.mateusz.database.spatial.domain.User
import com.nalepa.mateusz.database.spatial.infrastructure.MessageRepository
import com.nalepa.mateusz.database.spatial.infrastructure.UserRepository
import org.springframework.boot.CommandLineRunner

//@Component
class RunAtStart(
    private val ur: UserRepository,
    private val mr: MessageRepository
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        ur.createTable()
        mr.createTable()
        mr.deleteAll()
        ur.deleteAll()

        ur.create(User("swhite", "Skyler", "White"))
        ur.create(User("jpinkman", "Jesse", "Pinkman"))
        ur.create(User("wwhite", "Walter", "White"))
        ur.create(User("sgoodman", "Saul", "Goodman"))
        mr.create(Message("I AM THE ONE WHO KNOCKS!", "wwhite"))
    }

}