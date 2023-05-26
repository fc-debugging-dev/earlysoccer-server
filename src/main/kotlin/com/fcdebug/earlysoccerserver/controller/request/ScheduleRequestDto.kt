package com.fcdebug.earlysoccerserver.controller.request

import java.time.LocalDateTime

data class ScheduleRequestDto (
    val date: LocalDateTime,
    val place: String,
    val opponent: String,
) {
    override fun toString(): String =
        "{\"date\":\"$date\",\"place\":\"$place\",\"opponent\":\"$opponent\"}"
}