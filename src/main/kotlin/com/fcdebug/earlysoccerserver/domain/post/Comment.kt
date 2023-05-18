package com.fcdebug.earlysoccerserver.domain.post

import com.fcdebug.earlysoccerserver.domain.AuditDateTimeEntity
import com.fcdebug.earlysoccerserver.domain.member.Member
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne

@Entity
class Comment(
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Member::class) val writer: Member,
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Post::class) val post: Post,
    @Column(nullable = false) val content: String,
): AuditDateTimeEntity() {
}