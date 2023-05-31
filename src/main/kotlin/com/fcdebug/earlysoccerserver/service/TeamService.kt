package com.fcdebug.earlysoccerserver.service

import com.fcdebug.earlysoccerserver.controller.request.ScheduleRequestDto
import com.fcdebug.earlysoccerserver.controller.request.TeamRequestDto
import com.fcdebug.earlysoccerserver.controller.response.ScheduleResponseDto
import com.fcdebug.earlysoccerserver.domain.schedule.Schedule
import com.fcdebug.earlysoccerserver.domain.schedule.ScheduleRepository
import com.fcdebug.earlysoccerserver.domain.team.Team
import com.fcdebug.earlysoccerserver.domain.team.TeamDto
import com.fcdebug.earlysoccerserver.domain.team.TeamRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class TeamService (
    private val teamRepository: TeamRepository,
    private val scheduleRepository: ScheduleRepository
) {
    fun findTeamSchedules(teamId: Long, year: String?, month: String?, limit: Int?): List<ScheduleResponseDto> {
        limit?.run {
            return scheduleRepository.findByTeamIdByRecent(teamId, limit).map {
                ScheduleResponseDto.toDto(it) }
        }
        return scheduleRepository.findByTeamIdByYearByMonth(teamId, year, month).map { ScheduleResponseDto.toDto(it) }
    }

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

    fun updateTeamSchedules(scheduleId: Long, req: ScheduleRequestDto): ScheduleResponseDto {
        val schedule: Schedule = scheduleRepository.findById(scheduleId).orElse(null)
        schedule.run {
            this.updateSchedule(req)
            scheduleRepository.save(this)
        }
        return ScheduleResponseDto.toDto(schedule)
    }

    fun deleteTeamSchedules(scheduleId: Long) = scheduleRepository.deleteById(scheduleId)

    fun createTeam(req: TeamRequestDto): TeamDto =
        TeamDto.toDto(teamRepository.save(
            Team.create(name = req.name, teamImg = req.teamImg)
        ))
}