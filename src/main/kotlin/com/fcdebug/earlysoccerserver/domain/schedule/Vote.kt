package com.fcdebug.earlysoccerserver.domain.schedule

import com.fcdebug.earlysoccerserver.controller.request.VoteRequestDto
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
    @ManyToOne(fetch = FetchType.LAZY) val schedule: Schedule,
    @ManyToOne(fetch = FetchType.LAZY) val member: Member,
    @Enumerated(EnumType.STRING) var status: Status
): AuditDateTimeEntity() {

    companion object {
        fun create(schedule: Schedule, member: Member, status: String) =
            Vote(schedule, member, Status.valueOf(status))
    }

    fun updateVote(req: VoteRequestDto) {
        this.status = Status.valueOf(req.status)
    }
}

enum class Status {
    ATTENDED,
    ABSENT,
}