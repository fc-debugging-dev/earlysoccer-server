package com.fcdebug.earlysoccerserver.domain.team

import com.fcdebug.earlysoccerserver.domain.AuditDateTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany

@Entity
class Team(
    @Column(nullable = false) val name: String,

    val teamImg: String? = null,

    @OneToMany(mappedBy = "team") val teamMembers: MutableList<TeamMember> = mutableListOf(),

    @OneToMany(mappedBy = "team") val notifications: MutableList<Notification> = mutableListOf(),

): AuditDateTimeEntity() {
    fun addMember(member: TeamMember) = teamMembers.add(member)

    fun toDto(): TeamDto =
        TeamDto(
            id = this.id,
            name = this.name,
            teamImg = this.teamImg,
            teamMembers = this.teamMembers,
            notifications = this.notifications,
        )

    companion object {
        fun create(name: String, teamImg: String? = null): Team =
            Team(
                name = name,
                teamImg = teamImg
            )
    }
}