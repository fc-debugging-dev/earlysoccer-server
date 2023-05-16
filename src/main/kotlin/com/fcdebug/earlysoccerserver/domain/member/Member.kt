package com.fcdebug.earlysoccerserver.domain.member

import com.fcdebug.earlysoccerserver.domain.AuditDateTimeEntity
import com.fcdebug.earlysoccerserver.domain.team.TeamMember
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany

@Entity
class Member(
    @Column(nullable = false) val email: String,
    @Column(nullable = false) val password: String,
    @Column(nullable = false) val name: String,
    @Column(nullable = false) val nickname: String,
    val profileImg: String? = null,
    @OneToMany(mappedBy = "member") val memberTeams: MutableList<TeamMember> = mutableListOf<TeamMember>()
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
