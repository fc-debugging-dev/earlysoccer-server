package com.fcdebug.earlysoccerserver.controller

import com.fcdebug.earlysoccerserver.controller.request.ScheduleRequestDto
import com.fcdebug.earlysoccerserver.controller.request.TeamRequestDto
import com.fcdebug.earlysoccerserver.controller.response.ScheduleResponseDto
import com.fcdebug.earlysoccerserver.domain.team.TeamDto
import com.fcdebug.earlysoccerserver.service.TeamService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("team")
class TeamController(
    private val teamService: TeamService,
) {
    @PostMapping
    fun createTeam(@RequestBody req: TeamRequestDto): ResponseEntity<TeamDto> {
        val team: TeamDto = teamService.createTeam(req)
        return ResponseEntity(team, HttpStatus.CREATED)
    }

    @GetMapping("/{teamId}/schedules")
    fun findTeamSchedules(@PathVariable teamId: String): ResponseEntity<List<ScheduleResponseDto>> =
        ResponseEntity(teamService.findTeamSchedules(teamId.toLong()), HttpStatus.OK)

    @PostMapping("/{teamId}/schedules")
    fun createTeamSchedule(
        @PathVariable teamId: String,
        @RequestBody req: ScheduleRequestDto): ResponseEntity<ScheduleResponseDto> {
        val res: ScheduleResponseDto = teamService.createTeamSchedules(teamId.toLong(), req)
        return ResponseEntity(res, HttpStatus.CREATED)
    }

    @PutMapping("/{teamId}/schedules/{scheduleId}")
    fun updateTeamSchedule(
        @PathVariable scheduleId: String,
        @PathVariable teamId: String,
        @RequestBody req: ScheduleRequestDto): ResponseEntity<ScheduleResponseDto> {
        val res = teamService.updateTeamSchedules(scheduleId.toLong(), teamId.toLong(), req)
        return ResponseEntity(res, HttpStatus.OK)
    }

    @DeleteMapping("/schedules/{scheduleId}")
    fun deleteTeamSchedule(@PathVariable scheduleId: String): ResponseEntity<HttpStatus> {
        teamService.deleteTeamSchedules(scheduleId.toLong())
        return ResponseEntity(HttpStatus.OK)
    }
}
