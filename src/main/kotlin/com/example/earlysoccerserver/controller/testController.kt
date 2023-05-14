package com.example.earlysoccerserver.controller

import com.example.earlysoccerserver.domain.member.Member
import com.example.earlysoccerserver.domain.member.MemberDto
import com.example.earlysoccerserver.domain.member.MemberRepository
import com.example.earlysoccerserver.domain.team.TeamMemberRepository
import com.example.earlysoccerserver.domain.team.TeamRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.Optional

@RestController
class TestController(
    private val teamMemberRepository: TeamMemberRepository,
    private val memberRepository: MemberRepository,
    private val teamRepository: TeamRepository,
) {
    @GetMapping("/")
    fun findAll(): String{
        teamMemberRepository.findById(1).orElse(null)
        return "test"
    }
}