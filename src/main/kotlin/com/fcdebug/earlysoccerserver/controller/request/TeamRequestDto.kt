package com.fcdebug.earlysoccerserver.controller.request

data class TeamRequestDto(
    val name: String,
    val teamImg: String?,
) {
    override fun toString(): String =
        "{\"name\":\"$name\",\"teamImg\":\"$teamImg\"}"
}