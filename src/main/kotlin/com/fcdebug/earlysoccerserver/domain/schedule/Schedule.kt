package com.fcdebug.earlysoccerserver.domain.schedule

import com.fcdebug.earlysoccerserver.controller.request.ScheduleRequestDto
import com.fcdebug.earlysoccerserver.domain.AuditDateTimeEntity
import com.fcdebug.earlysoccerserver.domain.team.Team
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

@Entity
class Schedule(
    @ManyToOne(fetch = FetchType.LAZY) val team: Team,
    @Column(nullable = false) var date: LocalDateTime,
    @Column(nullable = false) var place: String,
    @Column(nullable = false) var opponent: String,
): AuditDateTimeEntity() {

    fun updateSchedule(req: ScheduleRequestDto) {
        this.date = req.date
        this.place = req.place
        this.opponent = req.opponent
    }

    companion object {
        fun create(team: Team, date: LocalDateTime, place: String, opponent: String): Schedule =
            Schedule(
                team = team,
                date = date,
                place = place,
                opponent = opponent,
            )
    }
}