package com.fcdebug.earlysoccerserver.controller

import com.fcdebug.earlysoccerserver.controller.request.CreateNotificationRequest
import com.fcdebug.earlysoccerserver.controller.request.ScheduleRequestDto
import com.fcdebug.earlysoccerserver.controller.request.TeamRequestDto
import com.fcdebug.earlysoccerserver.controller.request.UpdateNotificationRequest
import com.fcdebug.earlysoccerserver.controller.response.ScheduleResponseDto
import com.fcdebug.earlysoccerserver.domain.team.TeamDto
import com.fcdebug.earlysoccerserver.service.NotificationService
import com.fcdebug.earlysoccerserver.service.TeamService
import com.fcdebug.earlysoccerserver.service.request.CreateNotificationServiceRequestDto
import com.fcdebug.earlysoccerserver.service.request.UpdateNotificationServiceRequestDto
import com.fcdebug.earlysoccerserver.service.response.CreateNotificationServiceResponseDto
import com.fcdebug.earlysoccerserver.service.response.NotificationDetailServiceResponseDto
import com.fcdebug.earlysoccerserver.service.response.NotificationListServiceResponseDto
import com.fcdebug.earlysoccerserver.service.response.UpdateNotificationServiceResponseDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("team")
class TeamController(
    private val teamService: TeamService,
    private val notificationService: NotificationService,
) {
    @PostMapping
    fun createTeam(@RequestBody req: TeamRequestDto): ResponseEntity<TeamDto> {
        val team: TeamDto = teamService.createTeam(req)
        return ResponseEntity(team, HttpStatus.CREATED)
    }

    @GetMapping("/{teamId}/schedules")
    fun findTeamSchedules(
        @PathVariable teamId: String,
        @RequestParam("year") year: String?,
        @RequestParam("month") month: String?,
        @RequestParam("limit") limit: String?,): ResponseEntity<List<ScheduleResponseDto>> =
        ResponseEntity(teamService.findTeamSchedules(teamId.toLong(), year, month, limit?.toInt()),
            HttpStatus.OK)

    @PostMapping("/{teamId}/schedules")
    fun createTeamSchedule(
        @PathVariable teamId: String,
        @RequestBody req: ScheduleRequestDto): ResponseEntity<ScheduleResponseDto> =
        ResponseEntity(teamService.createTeamSchedules(teamId.toLong(), req), HttpStatus.CREATED)

    @PutMapping("/schedules/{scheduleId}")
    fun updateTeamSchedule(
        @PathVariable scheduleId: String,
        @RequestBody req: ScheduleRequestDto): ResponseEntity<ScheduleResponseDto> =
            ResponseEntity(teamService.updateTeamSchedules(scheduleId.toLong(), req), HttpStatus.OK)

    @DeleteMapping("/schedules/{scheduleId}")
    fun deleteTeamSchedule(@PathVariable scheduleId: String): ResponseEntity<HttpStatus> {
        teamService.deleteTeamSchedules(scheduleId.toLong())
        return ResponseEntity(HttpStatus.OK)
    }
    
    @GetMapping("/{teamId}/notifications")
    fun findTeamNotifications(@PathVariable teamId: Long)
    : ResponseEntity<List<NotificationListServiceResponseDto>> {
        val result = notificationService.findTeamNotifications(teamId)
        
        return ResponseEntity.ok().body(result)
    }
    
    @PostMapping("/{teamId}/notifications")
    fun createTeamNotification(@PathVariable teamId: Long, createNotificationRequest: CreateNotificationRequest)
    : ResponseEntity<CreateNotificationServiceResponseDto> {
        val result = notificationService.createNotification(
            CreateNotificationServiceRequestDto(
                title = createNotificationRequest.title,
                content = createNotificationRequest.content,
                memberId = createNotificationRequest.memberId,
                teamId = teamId,
            )
        )
        
        return ResponseEntity.ok().body(result)
    }
    
    @GetMapping("/notifications/{notificationId}")
    fun findTeamNotificationById(@PathVariable notificationId: Long)
    : ResponseEntity<NotificationDetailServiceResponseDto> {
        // 상세 조회
        val result = notificationService.findTeamNotificationById(notificationId)
        
        return ResponseEntity.ok().body(result)
    }
    
    @PutMapping("/notifications/{notificationId}")
    fun updateTeamNotificationById(
        @PathVariable notificationId: Long,
        @RequestBody updateNotificationRequest: UpdateNotificationRequest,
    ): ResponseEntity<UpdateNotificationServiceResponseDto> {
        // 수정
        val result = notificationService.updateNotificationById(UpdateNotificationServiceRequestDto(
            id = notificationId,
            title = updateNotificationRequest.title,
            content = updateNotificationRequest.content,
        ))
        
        return ResponseEntity.ok().body(result)
    }
    
    @DeleteMapping("/notifications/{notificationId}")
    fun deleteTeamNotificationById(@PathVariable notificationId: Long): ResponseEntity<Nothing> {
        // 삭제
        notificationService.deleteNotificationById(notificationId)
        
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }
}
