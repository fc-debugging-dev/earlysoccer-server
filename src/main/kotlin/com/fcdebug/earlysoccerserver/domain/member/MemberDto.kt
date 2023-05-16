package com.fcdebug.earlysoccerserver.domain.member

import com.fcdebug.earlysoccerserver.domain.team.TeamMember

data class MemberDto(
    val id: Long?,
    val email: String,
    val name: String,
    val nickname: String,
    val profileImg: String?,
    val teams: MutableList<TeamMember>,
) {
}