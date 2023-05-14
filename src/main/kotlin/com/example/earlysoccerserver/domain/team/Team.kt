package com.example.earlysoccerserver.domain.team

import com.example.earlysoccerserver.domain.AuditDateTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
class Team(
    @Column(nullable = false) private val name: String,
    @OneToMany(mappedBy = "team") private val teamMembers: MutableList<TeamMember> = mutableListOf<TeamMember>()
): AuditDateTimeEntity() {
    private lateinit var teamImg: String

    fun addMember(member: TeamMember) = teamMembers.add(member)
}