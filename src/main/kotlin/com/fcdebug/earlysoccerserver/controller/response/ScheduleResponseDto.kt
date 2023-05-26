package com.fcdebug.earlysoccerserver.controller.response

import com.fcdebug.earlysoccerserver.domain.schedule.Schedule
import java.time.LocalDateTime

data class ScheduleResponseDto(
    val id: Long?,
    val date: LocalDateTime,
    val place: String,
    val opponent: String,
) {
    companion object {
        fun toDto(schedule: Schedule) =
            ScheduleResponseDto(
                id = schedule.id,
                date = schedule.date,
                place = schedule.place,
                opponent = schedule.opponent,
            )
    }
}