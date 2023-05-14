package com.example.earlysoccerserver.domain.schedule

import com.example.earlysoccerserver.domain.AuditDateTimeEntity
import com.example.earlysoccerserver.domain.team.Team
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

@Entity
class Schedule(
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Team::class) private val team: Team,
    @Column(nullable = false) private val date: LocalDateTime,
    @Column(nullable = false) private val place: String,
    @Column(nullable = false) private val opponent: String,
): AuditDateTimeEntity() {
}