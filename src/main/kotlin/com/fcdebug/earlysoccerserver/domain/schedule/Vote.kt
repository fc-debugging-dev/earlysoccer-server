package com.fcdebug.earlysoccerserver.domain.schedule

import com.fcdebug.earlysoccerserver.domain.AuditDateTimeEntity
import com.fcdebug.earlysoccerserver.domain.member.Member
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne

@Entity
class Vote(
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Schedule::class) val schedule: Schedule,
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Member::class) val member: Member,
    @Enumerated(EnumType.STRING) val status: Status
): AuditDateTimeEntity() {
}

enum class Status {
    ATTENDED,
    ABSENT,
}