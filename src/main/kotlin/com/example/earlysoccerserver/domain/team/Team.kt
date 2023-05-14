package com.example.earlysoccerserver.domain.team

import com.example.earlysoccerserver.domain.AuditDateTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany

@Entity
class Team(
    @Column(nullable = false) private val name: String,
    private val teamImg: String? = null,
    @OneToMany(mappedBy = "team") private val teamMembers: MutableList<TeamMember> = mutableListOf<TeamMember>()
): AuditDateTimeEntity() {
    fun addMember(member: TeamMember) = teamMembers.add(member)
}