package com.fcdebug.earlysoccerserver.service

import com.fcdebug.earlysoccerserver.controller.request.ScheduleRequestDto
import com.fcdebug.earlysoccerserver.controller.request.TeamRequestDto
import com.fcdebug.earlysoccerserver.controller.response.ScheduleResponseDto
import com.fcdebug.earlysoccerserver.domain.schedule.Schedule
import com.fcdebug.earlysoccerserver.domain.schedule.ScheduleRepository
import com.fcdebug.earlysoccerserver.domain.team.Team
import com.fcdebug.earlysoccerserver.domain.team.TeamDto
import com.fcdebug.earlysoccerserver.domain.team.TeamRepository
import org.springframework.stereotype.Service

@Service
class TeamService (
    private val teamRepository: TeamRepository,
    private val scheduleRepository: ScheduleRepository
) {
    fun findTeamSchedules(id: Long): List<ScheduleResponseDto> =
        scheduleRepository.findByTeamId(id).map { ScheduleResponseDto.toDto(it) }

    fun createTeamSchedules(teamId: Long, req: ScheduleRequestDto): ScheduleResponseDto {
        val team: Team = teamRepository.getReferenceById(teamId)
        return ScheduleResponseDto.toDto(scheduleRepository.save(
            Schedule.create(
                team = team,
                date = req.date,
                place = req.place,
                opponent = req.opponent,
                note = req.note,
            )
        ))
    }

    fun updateTeamSchedules(scheduleId: Long, teamId: Long, req: ScheduleRequestDto): ScheduleResponseDto {
        val schedule: Schedule = scheduleRepository.findByIdAndTeamId(scheduleId, teamId)
        schedule.updateSchedule(req)
        return ScheduleResponseDto.toDto(scheduleRepository.save(schedule))
    }

    fun deleteTeamSchedules(scheduleId: Long) = scheduleRepository.deleteById(scheduleId)

    fun createTeam(req: TeamRequestDto): TeamDto =
        TeamDto.toDto(teamRepository.save(
            Team.create(name = req.name, teamImg = req.teamImg)
        ))
}