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
    private val profileImg: String? = null,
    @OneToMany(mappedBy = "member") private val memberTeams: MutableList<TeamMember> = mutableListOf<TeamMember>()
): AuditDateTimeEntity() {
    fun addTeam(team: TeamMember) = memberTeams.add(team)

    fun toDto() =
        MemberDto(
            id = this.id,
            email = this.email,
            name = this.name,
            nickname = this.nickname,
            profileImg = this.profileImg,
            teams = memberTeams
        )
}
