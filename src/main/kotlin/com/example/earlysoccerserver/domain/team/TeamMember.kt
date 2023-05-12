package com.example.earlysoccerserver.domain.team

import com.example.earlysoccerserver.domain.AuditDateTimeEntity
import com.example.earlysoccerserver.domain.member.Member
import jakarta.persistence.*

@Entity
class TeamMember(
    @Enumerated(EnumType.STRING) private val role: Role,
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Member::class) private val member: Member,
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Team::class) private val team: Team,
): AuditDateTimeEntity() {
}

enum class Role {
    OWNER,
    MANAGER,
    USER
}