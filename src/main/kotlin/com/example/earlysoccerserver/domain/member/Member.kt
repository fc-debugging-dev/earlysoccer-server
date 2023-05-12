package com.example.earlysoccerserver.domain.member

import com.example.earlysoccerserver.domain.AuditDateTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "member")
class Member(
    @Column(name = "email", nullable = false) private val email: String,
    @Column(name = "password", nullable = false) private val password: String,
    @Column(name = "name", nullable = false) private val name: String,
    @Column(name = "nickname", nullable = false) private val nickname: String,
    @Column(name = "profile_img") private val profileImg: String,
): AuditDateTimeEntity() { }