package com.fcdebug.earlysoccerserver.domain.team

import com.fcdebug.earlysoccerserver.domain.AuditDateTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany

@Entity
class Team(
    @Column(nullable = false) val name: String,
    val teamImg: String? = null,
    @OneToMany(mappedBy = "team") val teamMembers: MutableList<TeamMember> = mutableListOf<TeamMember>()
): AuditDateTimeEntity() {
    fun addMember(member: TeamMember) = teamMembers.add(member)
}