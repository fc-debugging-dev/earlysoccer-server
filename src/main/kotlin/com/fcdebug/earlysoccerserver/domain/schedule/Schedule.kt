package com.fcdebug.earlysoccerserver.domain.schedule

import com.fcdebug.earlysoccerserver.domain.AuditDateTimeEntity
import com.fcdebug.earlysoccerserver.domain.team.Team
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

@Entity
class Schedule(
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Team::class) val team: Team,
    @Column(nullable = false) val date: LocalDateTime,
    @Column(nullable = false) val place: String,
    @Column(nullable = false) val opponent: String,
): AuditDateTimeEntity() {
}