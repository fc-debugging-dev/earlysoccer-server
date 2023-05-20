package com.fcdebug.earlysoccerserver.domain.schedule

import com.fcdebug.earlysoccerserver.domain.team.Team
import java.time.LocalDateTime

data class ScheduleDto (
    val id: Long?,
    val team: Team,
    val date: LocalDateTime,
    val place: String,
    val opponent: String,
)