package com.fcdebug.earlysoccerserver.service

import com.fcdebug.earlysoccerserver.domain.member.MemberRepository
import com.fcdebug.earlysoccerserver.domain.team.Notification
import com.fcdebug.earlysoccerserver.domain.team.NotificationRepository
import com.fcdebug.earlysoccerserver.domain.team.TeamRepository
import com.fcdebug.earlysoccerserver.service.request.NotificationCreateServiceRequestDto
import com.fcdebug.earlysoccerserver.service.response.NotificationCreateServiceResponseDto
import org.springframework.stereotype.Service

@Service
class NotificationService (
    private val notificationRepository: NotificationRepository,
    private val memberRepository: MemberRepository,
    private val teamRepository: TeamRepository,
) {
    
    fun createNotification(notificationCreateServiceRequestDto: NotificationCreateServiceRequestDto)
    : NotificationCreateServiceResponseDto {
        val member = memberRepository.findById(notificationCreateServiceRequestDto.memberId).orElse(null)
            ?: throw IllegalArgumentException("등록되지 않은 유저입니다")
        
        val team = teamRepository.findById(notificationCreateServiceRequestDto.teamId).orElse(null)
            ?: throw IllegalArgumentException("등록되지 않은 팀입니다")
    
        val notification = notificationRepository.save(Notification.create(
            title = notificationCreateServiceRequestDto.title,
            content = notificationCreateServiceRequestDto.content,
            writer = member,
            team = team,
        ))
        
        return NotificationCreateServiceResponseDto(
            id = notification.id!!,
        )
    }
    
    fun getAllNotificationsByTeamId(teamId: Long) {
        val team = teamRepository.findById(teamId).orElse(null)
            ?: throw IllegalArgumentException("등록되지 않은 팀입니다")

    }
    
    fun getNotificationById() {
    
    }
    
}
