package com.fcdebug.earlysoccerserver.controller.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(CustomException::class)
    fun handleEntityNotFound(e: CustomException): ResponseEntity<String> {
        return ResponseEntity.status(e.errorCode.code).body(e.errorCode.message)
    }
}
