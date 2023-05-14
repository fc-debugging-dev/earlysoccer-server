package com.example.earlysoccerserver.config

import com.example.earlysoccerserver.domain.member.Member
import com.example.earlysoccerserver.domain.member.MemberDto
import com.example.earlysoccerserver.domain.member.MemberRepository
import com.example.earlysoccerserver.domain.team.*
import io.github.serpro69.kfaker.faker
import jakarta.transaction.Transactional
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener
import java.util.*

@Configuration
class DataGenerator(
    private val memberRepository: MemberRepository,
    private val teamRepository: TeamRepository,
    private val teamMemberRepository: TeamMemberRepository
) {
    val faker = faker {  }

    @EventListener(ApplicationReadyEvent::class)
    @Transactional
    fun generateDummy() {
        val members = generateMember(1)
        val teams = generateTeam(5)
        for (team in teams) {
            for (member in members) {
                generateTeamMember(member, team)
            }
        }
    }

    private fun generateMember(size: Int): MutableList<Member> {
        val members: MutableList<Member> = mutableListOf<Member>()
        for (i in 1..size) {
            memberRepository.save(
                Member(
                    email = faker.internet.email(),
                    password = "1234",
                    name = faker.name.name(),
                    nickname = faker.name.firstName()
                )
            ).apply { members.add(this) }
        }
        return members
    }

    private fun generateTeam(size: Int): MutableList<Team> {
        val teams: MutableList<Team> = mutableListOf<Team>()
        for (i in 1..size) {
            teamRepository.save(
                Team(
                    name = faker.name.firstName(),
                )
            ).apply { teams.add(this) }
        }
        return teams
    }

    private fun generateTeamMember(member: Member, team: Team) {
        teamMemberRepository.save(
            TeamMember(
                role = Role.USER,
                member = member,
                team = team
            )
        ).apply {
            member.addTeam(this)
            team.addMember(this)
        }
    }
}