package com.fcdebug.earlysoccerserver.controller.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val code: HttpStatus,
    val message: String,
) {
    NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "등록되지 않은 공지사항입니다"),
    TEAM_NOT_FOUND(HttpStatus.NOT_FOUND, "등록되지 않은 팀입니다"),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "등록되지 않은 유저입니다"),
}

class CustomException(val errorCode: ErrorCode) : RuntimeException()

