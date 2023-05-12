package com.example.earlysoccerserver.domain.team

import com.example.earlysoccerserver.domain.AuditDateTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
class Team(
    @Column(nullable = false) private val name: String,
    private val teamImg: String,
    @OneToMany(mappedBy = "team") private val teamMembers: List<TeamMember>,
): AuditDateTimeEntity() {
}