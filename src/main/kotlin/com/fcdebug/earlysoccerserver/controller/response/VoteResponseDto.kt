package com.fcdebug.earlysoccerserver.controller.response

import com.fcdebug.earlysoccerserver.domain.schedule.Vote

data class VoteResponseDto(
    val id: Long,
    val memberId: Long,
    val status: String,
) {
    companion object {
        fun toDto(vote: Vote): VoteResponseDto =
            VoteResponseDto(vote.id!!, vote.member.id!!, vote.status.toString())
    }
}