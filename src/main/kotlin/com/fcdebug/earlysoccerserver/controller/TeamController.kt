package com.fcdebug.earlysoccerserver.controller

import com.fcdebug.earlysoccerserver.controller.request.ScheduleRequestDto
import com.fcdebug.earlysoccerserver.controller.request.TeamRequestDto
import com.fcdebug.earlysoccerserver.controller.request.VoteRequestDto
import com.fcdebug.earlysoccerserver.controller.response.ScheduleResponseDto
import com.fcdebug.earlysoccerserver.controller.response.VoteResponseDto
import com.fcdebug.earlysoccerserver.domain.schedule.Vote
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
import org.springframework.web.bind.annotation.RequestParam
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
    fun findTeamSchedules(
        @PathVariable teamId: String,
        @RequestParam("year") year: String?,
        @RequestParam("month") month: String?,
        @RequestParam("start") start: String?,
        @RequestParam("end") end: String?,): ResponseEntity<List<ScheduleResponseDto>> =
        ResponseEntity(teamService.findTeamSchedules(teamId.toLong(), year, month, start, end),
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

    @PostMapping("/schedules/{scheduleId}/vote")
    fun voteTeamSchedule(
        @PathVariable scheduleId: String,
        @RequestBody req: VoteRequestDto): ResponseEntity<VoteResponseDto> {
        return ResponseEntity(teamService.createTeamScheduleVote(scheduleId.toLong(), req), HttpStatus.CREATED)
    }

    @PutMapping("/schedules/vote/{voteId}")
    fun updateVoteTeamSchedule(
        @PathVariable voteId: String,
        @RequestBody req: VoteRequestDto): ResponseEntity<VoteResponseDto> =
        ResponseEntity(teamService.updateTeamScheduleVotes(voteId.toLong(), req), HttpStatus.OK)
}
