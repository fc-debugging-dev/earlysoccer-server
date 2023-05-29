package com.fcdebug.earlysoccerserver.controller

import com.fcdebug.earlysoccerserver.domain.schedule.ScheduleDto
import com.fcdebug.earlysoccerserver.service.TeamService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("team")
class TeamController(
    private val teamService: TeamService,
) {
    @GetMapping("/{teamId}/schedules")
    fun findTeamSchedules(@PathVariable teamId: String): ResponseEntity<List<ScheduleDto>> =
        ResponseEntity(teamService.findTeamSchedules(teamId.toLong()), HttpStatus.OK)
}