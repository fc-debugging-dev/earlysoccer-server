package com.fcdebug.earlysoccerserver.domain.schedule

import com.fcdebug.earlysoccerserver.controller.request.ScheduleRequestDto
import com.fcdebug.earlysoccerserver.domain.team.Team
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.querydsl.from.fetch
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.linecorp.kotlinjdsl.spring.data.updateQuery
import jakarta.persistence.Query
import jakarta.persistence.criteria.JoinType
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter

interface ScheduleRepository: JpaRepository<Schedule, Long>, ScheduleJdslRepository {
}

interface ScheduleJdslRepository {
    fun findByTeamIdByRange(teamId: Long, start: String, end: String): List<Schedule>

    fun findByTeamIdByYearMonth(teamId: Long, year: String?, month: String?): List<Schedule>

    fun updateSchedule(scheduleId: Long, req: ScheduleRequestDto): Query
}

class ScheduleJdslRepositoryImpl(
    private val queryFactory: SpringDataQueryFactory
): ScheduleJdslRepository {

    private fun scheduleListQuery(teamId: Long, start: LocalDateTime, end: LocalDateTime, limit: Int? = null): List<Schedule> {
        return queryFactory.listQuery<Schedule> {
            selectDistinct(entity(Schedule::class))
            from(entity(Schedule::class))
            fetch(Schedule::team)
            fetch(Schedule::votes, JoinType.LEFT)
            fetch(Vote::member, JoinType.LEFT)
            whereAnd(
                column(Team::id).equal(teamId),
                column(Schedule::date).between(start, end)
            )
            orderBy(column(Schedule::date).asc())
            limit?.run { limit(this) }
        }
    }
    override fun findByTeamIdByRange(teamId: Long, start: String, end: String): List<Schedule> {
        val startDate: LocalDateTime = LocalDateTime.parse(start)
        val endDate: LocalDateTime = LocalDateTime.parse(end)
        return scheduleListQuery(teamId, startDate, endDate, 5)
    }

    override fun findByTeamIdByYearMonth(teamId: Long, year: String?, month: String?): List<Schedule> {
        val standard: YearMonth = YearMonth.parse("$year-$month", DateTimeFormatter.ofPattern("yyyy-MM"))
        val start: LocalDateTime = standard.atDay(1).atTime(0,0,0)
        val end: LocalDateTime = standard.atEndOfMonth().atTime(23,59,59)
        return scheduleListQuery(teamId, start, end)
    }

    override fun updateSchedule(scheduleId: Long, req: ScheduleRequestDto): Query {
        return queryFactory.updateQuery<Schedule> {
            where(col(Schedule::id).equal(scheduleId))
            setParams(col(Schedule::place) to req.place)
            setParams(col(Schedule::date) to req.date)
            setParams(col(Schedule::opponent) to req.opponent)
            setParams(col(Schedule::note) to req.note)
            setParams(col(Schedule::updatedAt) to LocalDateTime.now())
        }
    }
}