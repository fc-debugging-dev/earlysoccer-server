package com.example.earlysoccerserver.domain.team

import org.springframework.data.jpa.repository.JpaRepository

interface TeamRepository: JpaRepository<Team, Long> {
}

interface TeamMemberRepository: JpaRepository<TeamMember, Long> {

}