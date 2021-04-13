package com.nalepa.mateusz.database.spatial.api

import org.springframework.http.MediaType
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException
import java.util.*
import java.util.Collections.synchronizedSet

class SseBroadcaster {

    private val sseEmitters = synchronizedSet(HashSet<SseEmitter>())

    fun subscribe(): SseEmitter =
        SseEmitter().also {
            this.sseEmitters.add(it)
            it.onCompletion { this.sseEmitters.remove(it) }
        }


    fun send(messageDto: MessageDto) {
        synchronized(sseEmitters) {
            sseEmitters.forEach { emitter ->
                // Servlet containers don't always detect ghost connection, so we must catch exceptions ...
                try {
                    emitter.send(messageDto, MediaType.APPLICATION_JSON)
                } catch (e: IOException) {
                }
            }
        }
    }
}