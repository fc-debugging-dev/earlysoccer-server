package com.fcdebug.earlysoccerserver.service

import com.fcdebug.earlysoccerserver.controller.request.ScheduleRequestDto
import com.fcdebug.earlysoccerserver.controller.request.TeamRequestDto
import com.fcdebug.earlysoccerserver.controller.request.VoteRequestDto
import com.fcdebug.earlysoccerserver.controller.response.MemberResponseDto
import com.fcdebug.earlysoccerserver.controller.response.ScheduleResponseDto
import com.fcdebug.earlysoccerserver.controller.response.VoteResponseDto
import com.fcdebug.earlysoccerserver.domain.member.Member
import com.fcdebug.earlysoccerserver.domain.member.MemberRepository
import com.fcdebug.earlysoccerserver.domain.schedule.*
import com.fcdebug.earlysoccerserver.domain.team.Team
import com.fcdebug.earlysoccerserver.domain.team.TeamDto
import com.fcdebug.earlysoccerserver.domain.team.TeamRepository
import jakarta.transaction.Transactional
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class TeamService (
    private val teamRepository: TeamRepository,
    private val scheduleRepository: ScheduleRepository,
    private val voteRepository: VoteRepository,
    private val memberRepository: MemberRepository,
) {
    val log = KotlinLogging.logger {  }
    fun findTeamSchedules(teamId: Long, year: String?, month: String?,
                          start: String?, end: String?): List<ScheduleResponseDto> {
        var scheduleList = listOf<Schedule>()
        if (year != null && month != null) {
            scheduleRepository.findByTeamIdByYearMonth(teamId, year, month).also { scheduleList = it }
        } else if (start != null && end != null) {
            scheduleRepository.findByTeamIdByRange(teamId, start, end).also { scheduleList = it }
        }
        return scheduleList.map { schedule ->
            val attended: MutableList<MemberResponseDto> = mutableListOf()
            val absent: MutableList<MemberResponseDto> = mutableListOf()
            schedule.votes.forEach { vote ->
                if (vote.status == Status.ATTENDED) attended.add(MemberResponseDto.toDto(vote.member))
                else if (vote.status == Status.ABSENT) absent.add(MemberResponseDto.toDto(vote.member))
            }
            ScheduleResponseDto.toDto(schedule, attended, absent)
        }
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
        ), mutableListOf(), mutableListOf())
    }

    @Transactional
    fun updateTeamSchedules(scheduleId: Long, req: ScheduleRequestDto) {
        scheduleRepository.updateSchedule(scheduleId, req).executeUpdate()
    }

    fun deleteTeamSchedules(scheduleId: Long) = scheduleRepository.deleteById(scheduleId)

    fun createTeam(req: TeamRequestDto): TeamDto =
        TeamDto.toDto(teamRepository.save(
            Team.create(name = req.name, teamImg = req.teamImg)
        ))

    fun createTeamScheduleVote(scheduleId: Long, req: VoteRequestDto): VoteResponseDto {
        val schedule: Schedule = scheduleRepository.getReferenceById(scheduleId)
        val member: Member = memberRepository.getReferenceById(req.memberId)
        return VoteResponseDto.toDto(voteRepository.save(
            Vote.create(schedule, member, req.status)
        ))
    }

    @Transactional
    fun updateTeamScheduleVotes(voteId: Long, req: VoteRequestDto) =
        voteRepository.updateVote(voteId, req).executeUpdate()
}