package com.example.earlysoccerserver.domain.member

import com.example.earlysoccerserver.domain.team.TeamMember

data class MemberDto(
    val id: Long?,
    val email: String,
    val password: String,
    val name: String,
    val teams: MutableList<TeamMember>,
) {
}