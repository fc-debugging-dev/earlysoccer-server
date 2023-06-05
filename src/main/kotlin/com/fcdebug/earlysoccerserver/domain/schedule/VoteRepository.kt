package com.fcdebug.earlysoccerserver.domain.schedule

import org.springframework.data.jpa.repository.JpaRepository

interface VoteRepository: JpaRepository<Vote, Long> {
}