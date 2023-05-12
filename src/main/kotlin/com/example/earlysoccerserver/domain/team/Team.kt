package com.example.earlysoccerserver.domain.team

import com.example.earlysoccerserver.domain.AuditDateTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "team")
class Team(
    @Column(nullable = false) private val name: String,
    private val teamImg: String,
): AuditDateTimeEntity() {
}