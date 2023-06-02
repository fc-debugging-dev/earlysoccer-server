package com.fcdebug.earlysoccerserver.service.request

data class NotificationCreateServiceRequestDto(
    
    val title: String,
    
    val content: String,
    
    val memberId: Long,
    
    val teamId: Long,
)
