package com.fcdebug.earlysoccerserver.domain.team

import com.fcdebug.earlysoccerserver.domain.AuditDateTimeEntity
import com.fcdebug.earlysoccerserver.domain.member.Member
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne

@Entity
class TeamMember(
    @Enumerated(EnumType.STRING) val role: Role,
    @ManyToOne(fetch = FetchType.LAZY) val member: Member,
    @ManyToOne(fetch = FetchType.LAZY) val team: Team,
): AuditDateTimeEntity() {

    companion object {
        fun create(role: String, member: Member, team: Team) =
            TeamMember(Role.valueOf(role), member, team)
    }
}
enum class Role {
    OWNER,
    MANAGER,
    USER
}