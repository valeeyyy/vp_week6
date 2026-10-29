package com.valerie.vp_week6.util

import java.util.UUID

object Messages {
    const val JOIN_FIRST = "Harap bergabung ke komunitas terlebih dahulu!"
    const val NO_CLUBS = "Belum bergabung dengan komunitas"
    const val NO_PROJECTS = "Belum ada proyek yang dicatat"
}

fun generateId(): String = UUID.randomUUID().toString()

sealed interface UiEvent {
    data class ShowMessage(val message: String) : UiEvent
}
