package com.fcdebug.earlysoccerserver.domain.team

data class TeamDto(
    val id: Long?,
    val name: String,
    val teamImg: String?,
    val teamMembers: MutableList<TeamMember>,
    val notifications: MutableList<Notification>,
)