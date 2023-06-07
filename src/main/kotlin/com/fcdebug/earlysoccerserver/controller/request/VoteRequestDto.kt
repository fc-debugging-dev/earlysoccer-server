package com.fcdebug.earlysoccerserver.controller.request

data class VoteRequestDto(
    val memberId: Long,
    val status: String
) {
    override fun toString(): String =
        "{\"memberId\":\"$memberId\",\"status\":\"$status\"}"
}