package com.fcdebug.earlysoccerserver.domain.team

import com.fcdebug.earlysoccerserver.domain.AuditDateTimeEntity
import com.fcdebug.earlysoccerserver.domain.member.Member
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne

@Entity
class Notification(
    
    @Column(nullable = false) var title: String,
    
    @Column(nullable = false) var content: String,
    
    @ManyToOne(fetch = FetchType.LAZY)
    val writer: Member,
    
    @ManyToOne(fetch = FetchType.LAZY)
    val team: Team,
    
    ) : AuditDateTimeEntity() {
    
    fun update(title: String, content: String) {
        this.title = title
        this.content = content
    }
    
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
