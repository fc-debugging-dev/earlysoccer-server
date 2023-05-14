package com.example.earlysoccerserver.domain.post

import com.example.earlysoccerserver.domain.AuditDateTimeEntity
import com.example.earlysoccerserver.domain.member.Member
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne

@Entity
class Comment(
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Member::class) private val writer: Member,
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Post::class) private val post: Post,
    @Column(nullable = false) private val content: String,
): AuditDateTimeEntity() {
}