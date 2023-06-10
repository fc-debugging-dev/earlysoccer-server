package com.fcdebug.earlysoccerserver.controller.request

data class CreateNotificationRequest(
    val title: String,
    val content: String,
    val memberId: Long,
)

data class UpdateNotificationRequest(
    val title: String,
    val content: String,
)
