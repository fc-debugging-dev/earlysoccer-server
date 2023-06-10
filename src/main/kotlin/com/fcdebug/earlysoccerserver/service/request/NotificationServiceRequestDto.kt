package com.fcdebug.earlysoccerserver.service.request

data class CreateNotificationServiceRequestDto(
    val title: String,
    val content: String,
    val memberId: Long,
    val teamId: Long,
)

data class UpdateNotificationServiceRequestDto(
    val id: Long,
    val title: String,
    val content: String,
)
