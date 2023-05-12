package com.example.earlysoccerserver.domain.member

import com.example.earlysoccerserver.domain.AuditDateTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "member")
class Member(
    email: String,
    password: String,
    name: String,
    nickname: String,
    profileImg: String
): AuditDateTimeEntity() {
    @Column(name = "email") @NotBlank
    val email: String = email

    @Column(name = "password") @NotBlank
    val password: String = password

    @Column(name = "name") @NotBlank
    val name: String = name

    @Column(name = "nickname") @NotBlank
    val nickname: String = nickname

    @Column(name = "profile_img") @NotBlank
    val profileImg: String = profileImg
}