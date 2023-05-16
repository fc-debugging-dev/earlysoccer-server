package com.example.earlysoccerserver.domain.team

import com.example.earlysoccerserver.domain.AuditDateTimeEntity
import com.example.earlysoccerserver.domain.member.Member
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne

@Entity
class Notification(

    @Column(nullable = false) val title: String,

    @Column(nullable = false) val content: String,

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Member::class)
    val writer: Member,

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Member::class)
    val team: Team,

) : AuditDateTimeEntity() {
    
    companion object {
        fun create(title: String, content: String, team: Team, writer: Member): Notification {
            return Notification(
                title = title,
                content = content,
                team = team,
                writer = writer,
            )
        }
    }
}