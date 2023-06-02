package com.fcdebug.earlysoccerserver.domain.post

import com.fcdebug.earlysoccerserver.domain.AuditDateTimeEntity
import com.fcdebug.earlysoccerserver.domain.member.Member
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne


@Entity
class Post(
    @ManyToOne(fetch = FetchType.LAZY) val writer: Member,
    @Column(nullable = false) val title: String,
    @Column(nullable = false) val content: String,
    @Enumerated(EnumType.STRING) val category: Category
    ): AuditDateTimeEntity() {
}

enum class Category {
    TEAM_REVIEW,
    STADIUM_REVIEW,
    FREE_BOARD
}