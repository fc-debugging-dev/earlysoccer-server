package com.fcdebug.earlysoccerserver.controller.response

import com.fcdebug.earlysoccerserver.domain.member.Member

data class MemberResponseDto(
    val id: Long,
    val name: String,
    val nickname: String,
    val profileImg: String?,
) {
    companion object {
        fun toDto(member: Member) =
            MemberResponseDto(
                member.id!!,
                member.name,
                member.nickname,
                member.profileImg
            )
    }
}