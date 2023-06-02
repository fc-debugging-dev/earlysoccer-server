package com.fcdebug.earlysoccerserver.controller.request

import java.time.LocalDateTime

data class ScheduleRequestDto (
    val date: LocalDateTime,
    val place: String,
    val opponent: String,
    val note: String,
) {
    override fun toString(): String =
        "{\"date\":\"$date\",\"place\":\"$place\",\"opponent\":\"$opponent\",\"note\":\"$note\"}"
}