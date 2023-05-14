package com.example.earlysoccerserver.domain.team

data class TeamDto (
    val name: String,
    val teamImg: String?,
    val teamMembers: MutableList<TeamMember>
){
}