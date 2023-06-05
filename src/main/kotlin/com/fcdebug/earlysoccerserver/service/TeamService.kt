package com.fcdebug.earlysoccerserver.service

import com.fcdebug.earlysoccerserver.controller.request.ScheduleRequestDto
import com.fcdebug.earlysoccerserver.controller.request.TeamRequestDto
import com.fcdebug.earlysoccerserver.controller.request.VoteRequestDto
import com.fcdebug.earlysoccerserver.controller.response.MemberResponseDto
import com.fcdebug.earlysoccerserver.controller.response.ScheduleResponseDto
import com.fcdebug.earlysoccerserver.domain.member.Member
import com.fcdebug.earlysoccerserver.domain.member.MemberRepository
import com.fcdebug.earlysoccerserver.domain.schedule.*
import com.fcdebug.earlysoccerserver.domain.team.Team
import com.fcdebug.earlysoccerserver.domain.team.TeamDto
import com.fcdebug.earlysoccerserver.domain.team.TeamRepository
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
    fun findTeamSchedules(teamId: Long, year: String?, month: String?, limit: Int?): List<ScheduleResponseDto> {
        val attended: MutableList<MemberResponseDto> = mutableListOf()
        val absent: MutableList<MemberResponseDto> = mutableListOf()
        limit?.run {
            return scheduleRepository.findByTeamIdByRecent(teamId, limit).map { schedule ->
                schedule.votes.forEach { vote ->
                    if (vote.status == Status.ATTENDED) attended.add(MemberResponseDto.toDto(vote.member))
                    else if (vote.status == Status.ABSENT) absent.add(MemberResponseDto.toDto(vote.member))
                }
                ScheduleResponseDto.toDto(schedule, attended, absent) }
        }
        return scheduleRepository.findByTeamIdByYearByMonth(teamId, year, month).map {schedule ->
            schedule.votes.forEach { vote ->
                if (vote.status == Status.ATTENDED) attended.add(MemberResponseDto.toDto(vote.member))
                else if (vote.status == Status.ABSENT) absent.add(MemberResponseDto.toDto(vote.member))
            }
            ScheduleResponseDto.toDto(schedule, attended, absent) }
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
        ), mutableListOf<MemberResponseDto>(), mutableListOf<MemberResponseDto>())
    }

    fun updateTeamSchedules(scheduleId: Long, req: ScheduleRequestDto): ScheduleResponseDto {
        val schedule: Schedule = scheduleRepository.findById(scheduleId).orElse(null)
        schedule.run {
            this.updateSchedule(req)
            scheduleRepository.save(this)
        }
        return ScheduleResponseDto.toDto(schedule,
            mutableListOf<MemberResponseDto>(), mutableListOf<MemberResponseDto>())
    }

    fun deleteTeamSchedules(scheduleId: Long) = scheduleRepository.deleteById(scheduleId)

    fun createTeam(req: TeamRequestDto): TeamDto =
        TeamDto.toDto(teamRepository.save(
            Team.create(name = req.name, teamImg = req.teamImg)
        ))

    fun createTeamScheduleVote(scheduleId: Long, req: VoteRequestDto) {
        val schedule: Schedule = scheduleRepository.getReferenceById(scheduleId)
        val member: Member = memberRepository.getReferenceById(req.memberId)
        voteRepository.save(
            Vote.create(schedule, member, req.status)
        )
    }
}