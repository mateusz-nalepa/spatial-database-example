package com.nalepa.mateusz.database.spatial.helper

import java.io.File


class TimeSaver {

    fun saveWriteTime(times: List<Long>) {
        save(File("writeTimes.csv"), times)
    }

    fun saveReadTimes(times: List<Long>) {
        save(File("readTimes.csv"), times)
    }

    private fun save(file: File, times: List<Long>) {
        val writer = file.bufferedWriter()
        times.forEach { writer.write("$it\n") }
        writer.flush()
    }

}