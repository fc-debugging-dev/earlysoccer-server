package com.example.earlysoccerserver.domain.member

import com.example.earlysoccerserver.domain.AuditDateTimeEntity
import com.example.earlysoccerserver.domain.team.TeamMember
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
class Member(
    @Column(nullable = false) private val email: String,
    @Column(nullable = false) private val password: String,
    @Column(nullable = false) private val name: String,
    @Column(nullable = false) private val nickname: String,
    private val profileImg: String,
    @OneToMany(mappedBy = "member") private val memberTeams: List<TeamMember>,
): AuditDateTimeEntity() { }
