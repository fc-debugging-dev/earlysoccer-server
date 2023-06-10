package com.fcdebug.earlysoccerserver.service.response

import java.time.LocalDateTime

data class CreateNotificationServiceResponseDto(
    val id: Long,
)

data class UpdateNotificationServiceResponseDto(
    val id: Long,
)

data class NotificationListServiceResponseDto(
    val id: Long,
    val title: String,
    val writer: String,
    val createdAt: LocalDateTime,
)

data class NotificationDetailServiceResponseDto(
    val id: Long,
    val title: String,
    val content: String,
    val writer: String,
    val createdAt: LocalDateTime,
)
