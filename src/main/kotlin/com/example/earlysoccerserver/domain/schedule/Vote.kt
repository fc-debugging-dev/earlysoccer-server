package com.example.earlysoccerserver.domain.schedule

import com.example.earlysoccerserver.domain.AuditDateTimeEntity
import com.example.earlysoccerserver.domain.member.Member
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne

@Entity
class Vote(
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Schedule::class) private val schedule: Schedule,
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Member::class) private val member: Member,
    @Enumerated(EnumType.STRING) private val status: Status
): AuditDateTimeEntity() {
}

enum class Status {
    ATTENDED,
    ABSENT,
}