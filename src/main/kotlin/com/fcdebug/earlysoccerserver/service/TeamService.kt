package com.fcdebug.earlysoccerserver.service

import com.fcdebug.earlysoccerserver.domain.schedule.Schedule
import com.fcdebug.earlysoccerserver.domain.schedule.ScheduleRepository
import org.springframework.stereotype.Service

@Service
class TeamServiceImpl (
    val scheduleRepository: ScheduleRepository
) {
    fun findTeamSchedules(id: Long): List<Schedule> =
        scheduleRepository.findByTeamId(id)
}