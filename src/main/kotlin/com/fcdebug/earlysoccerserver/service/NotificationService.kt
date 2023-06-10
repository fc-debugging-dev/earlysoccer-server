package com.fcdebug.earlysoccerserver.service

import com.fcdebug.earlysoccerserver.controller.exception.CustomException
import com.fcdebug.earlysoccerserver.controller.exception.ErrorCode
import com.fcdebug.earlysoccerserver.domain.member.MemberRepository
import com.fcdebug.earlysoccerserver.domain.team.Notification
import com.fcdebug.earlysoccerserver.domain.team.NotificationRepository
import com.fcdebug.earlysoccerserver.domain.team.TeamRepository
import com.fcdebug.earlysoccerserver.service.request.CreateNotificationServiceRequestDto
import com.fcdebug.earlysoccerserver.service.request.UpdateNotificationServiceRequestDto
import com.fcdebug.earlysoccerserver.service.response.CreateNotificationServiceResponseDto
import com.fcdebug.earlysoccerserver.service.response.NotificationDetailServiceResponseDto
import com.fcdebug.earlysoccerserver.service.response.NotificationListServiceResponseDto
import com.fcdebug.earlysoccerserver.service.response.UpdateNotificationServiceResponseDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class NotificationService (
    private val notificationRepository: NotificationRepository,
    private val memberRepository: MemberRepository,
    private val teamRepository: TeamRepository,
) {
    
    @Transactional
    fun createNotification(createNotificationServiceRequestDto: CreateNotificationServiceRequestDto)
    : CreateNotificationServiceResponseDto {
        val member = memberRepository.findById(createNotificationServiceRequestDto.memberId)
            .orElseThrow {
                CustomException(ErrorCode.MEMBER_NOT_FOUND)
            }
        
        val team = teamRepository.findById(createNotificationServiceRequestDto.teamId).orElseThrow {
            CustomException(ErrorCode.TEAM_NOT_FOUND)
        }
    
        val notification = notificationRepository.save(Notification.create(
            title = createNotificationServiceRequestDto.title,
            content = createNotificationServiceRequestDto.content,
            writer = member,
            team = team,
        ))
        
        return CreateNotificationServiceResponseDto(
            id = notification.id!!,
        )
    }
    
    fun findTeamNotifications(teamId: Long): List<NotificationListServiceResponseDto> {
        val notifications = notificationRepository.findAll()
        
        return notifications.map {
            NotificationListServiceResponseDto(
                id = it.id!!,
                title = it.title,
                writer = it.writer.name,
                createdAt = it.createdAt,
            )
        }
        
    }
    
    fun findTeamNotificationById(notificationId: Long): NotificationDetailServiceResponseDto {
        val notification = notificationRepository.findById(notificationId)
            .orElseThrow {
                CustomException(ErrorCode.NOTIFICATION_NOT_FOUND)
            }
        
        return NotificationDetailServiceResponseDto(
            id = notification.id!!,
            title = notification.title,
            content = notification.content,
            writer = notification.writer.name,
            createdAt = notification.createdAt,
        )
    }
    
    @Transactional
    fun deleteNotificationById(notificationId: Long)  {
    
        val notification = notificationRepository.findById(notificationId)
            .orElseThrow {
                CustomException(ErrorCode.NOTIFICATION_NOT_FOUND)
            }
        
        notificationRepository.delete(notification)
    }
    
    @Transactional
    fun updateNotificationById(updateNotificationServiceRequestDto: UpdateNotificationServiceRequestDto)
    : UpdateNotificationServiceResponseDto {
        val notification = notificationRepository.findById(updateNotificationServiceRequestDto.id).orElseThrow {
            CustomException(ErrorCode.NOTIFICATION_NOT_FOUND)
        }
        
        notification.update(
            title = updateNotificationServiceRequestDto.title,
            content = updateNotificationServiceRequestDto.content,
        )
    
        val updatedNotification = notificationRepository.save(notification)
        
        return UpdateNotificationServiceResponseDto(
            id = updatedNotification.id!!
        )
    }
}
