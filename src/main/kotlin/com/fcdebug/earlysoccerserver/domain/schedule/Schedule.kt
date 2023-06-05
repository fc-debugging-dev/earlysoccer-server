package com.fcdebug.earlysoccerserver.domain.schedule

import com.fcdebug.earlysoccerserver.controller.request.ScheduleRequestDto
import com.fcdebug.earlysoccerserver.domain.AuditDateTimeEntity
import com.fcdebug.earlysoccerserver.domain.team.Team
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import java.time.LocalDateTime

@Entity
class Schedule(
    @ManyToOne(fetch = FetchType.LAZY) val team: Team,
    @Column(nullable = false) var date: LocalDateTime,
    @Column(nullable = false) var place: String,
    @Column(nullable = false) var opponent: String,
    var note: String,
    @OneToMany(mappedBy = "schedule") val votes: MutableList<Vote> = mutableListOf()
): AuditDateTimeEntity() {

    fun updateSchedule(req: ScheduleRequestDto) {
        this.date = req.date
        this.place = req.place
        this.opponent = req.opponent
        this.note = req.note
    }

    companion object {
        fun create(team: Team, date: LocalDateTime, place: String, opponent: String, note: String): Schedule =
            Schedule(team, date, place, opponent, note)
    }
}