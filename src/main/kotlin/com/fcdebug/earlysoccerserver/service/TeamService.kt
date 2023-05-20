package com.fcdebug.earlysoccerserver.service

import com.fcdebug.earlysoccerserver.domain.schedule.ScheduleDto
import com.fcdebug.earlysoccerserver.domain.schedule.ScheduleRepository
import org.springframework.stereotype.Service

@Service
class TeamService (
    private val scheduleRepository: ScheduleRepository
) {
    fun findTeamSchedules(id: Long): List<ScheduleDto> =
        scheduleRepository.findByTeamId(id).map { it.toEntity() }
}