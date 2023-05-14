package com.example.earlysoccerserver.domain.member

import com.example.earlysoccerserver.domain.team.TeamMember

data class MemberDto(
    val id: Long?,
    val email: String,
    val name: String,
    val nickname: String,
    val profileImg: String?,
    val teams: MutableList<TeamMember>,
) {
}