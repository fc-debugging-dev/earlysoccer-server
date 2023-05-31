package com.fcdebug.earlysoccerserver.domain.schedule

import org.springframework.data.jpa.repository.JpaRepository

interface ScheduleRepository: JpaRepository<Schedule, Long> {
    fun findByTeamId(id: Long): List<Schedule>
}