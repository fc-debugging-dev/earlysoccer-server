package com.fcdebug.earlysoccerserver.domain.team

data class TeamDto(
    val id: Long?,
    val name: String,
    val teamImg: String?,
    val teamMembers: MutableList<TeamMember>,
    val notifications: MutableList<Notification>,
) {
    companion object {
        fun toDto(team: Team): TeamDto =
            TeamDto(
                id = team.id,
                name = team.name,
                teamImg = team.teamImg,
                teamMembers = team.teamMembers,
                notifications = team.notifications,
            )
    }
}