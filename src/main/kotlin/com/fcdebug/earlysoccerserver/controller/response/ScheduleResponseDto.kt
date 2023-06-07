package com.fcdebug.earlysoccerserver.controller.response

import com.fcdebug.earlysoccerserver.domain.schedule.Schedule
import java.time.LocalDateTime

data class ScheduleResponseDto(
    val id: Long?,
    val date: LocalDateTime,
    val place: String,
    val opponent: String,
    val note: String,
    val attended: MutableList<MemberResponseDto> = mutableListOf(),
    val absent: MutableList<MemberResponseDto> = mutableListOf(),
) {
    companion object {
        fun toDto(schedule: Schedule,
                  attended: MutableList<MemberResponseDto>,
                  absent: MutableList<MemberResponseDto>) =
            ScheduleResponseDto(
                id = schedule.id,
                date = schedule.date,
                place = schedule.place,
                opponent = schedule.opponent,
                note = schedule.note,
                attended = attended,
                absent = absent,
            )
    }
}