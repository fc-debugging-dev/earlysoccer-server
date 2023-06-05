package com.fcdebug.earlysoccerserver.domain.schedule

import com.fcdebug.earlysoccerserver.domain.team.Team
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.querydsl.from.fetch
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import jakarta.persistence.criteria.JoinType
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter

interface ScheduleRepository: JpaRepository<Schedule, Long>, ScheduleJdslRepository {
}

interface ScheduleJdslRepository {
    fun findByTeamIdByRecent(teamId: Long, limit: Int): List<Schedule>

    fun findByTeamIdByYearByMonth(teamId: Long, year: String?, month: String?): List<Schedule>
}

class ScheduleJdslRepositoryImpl(
    private val queryFactory: SpringDataQueryFactory
): ScheduleJdslRepository {
    override fun findByTeamIdByRecent(teamId: Long, limit: Int): List<Schedule> {
        val standard: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM"))
        val end: LocalDateTime = YearMonth.parse(standard).atEndOfMonth().atTime(23,59,59)
        return queryFactory.listQuery<Schedule> {
            selectDistinct(entity(Schedule::class))
            from(entity(Schedule::class))
            fetch(Schedule::team)
            fetch(Schedule::votes, JoinType.LEFT)
            fetch(Vote::member, JoinType.LEFT)
            whereAnd(
                column(Team::id).equal(teamId),
                column(Schedule::date).between(LocalDateTime.now(), end)
            )
            orderBy(column(Schedule::date).asc())
            limit(limit)
        }
    }

    override fun findByTeamIdByYearByMonth(teamId: Long, year: String?, month: String?): List<Schedule> {
        val standard: YearMonth = YearMonth.parse("$year-$month", DateTimeFormatter.ofPattern("yyyy-MM"))
        val start: LocalDateTime = standard.atDay(1).atTime(0,0,0)
        val end: LocalDateTime = standard.atEndOfMonth().atTime(23,59,59)
        return queryFactory.listQuery<Schedule> {
            select(entity(Schedule::class))
            from(entity(Schedule::class))
            fetch(Schedule::team)
            fetch(Schedule::votes, JoinType.LEFT)
            fetch(Vote::member, JoinType.LEFT)
            whereAnd(
                column(Team::id).equal(teamId),
                column(Schedule::date).between(start, end)
            )
        }
    }
}