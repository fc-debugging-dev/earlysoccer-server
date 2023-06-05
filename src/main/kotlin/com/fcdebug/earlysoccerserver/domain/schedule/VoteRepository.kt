package com.fcdebug.earlysoccerserver.domain.schedule

import com.fcdebug.earlysoccerserver.controller.request.VoteRequestDto
import com.linecorp.kotlinjdsl.QueryFactory
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.updateQuery
import com.linecorp.kotlinjdsl.updateQuery
import jakarta.persistence.Query
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface VoteRepository: JpaRepository<Vote, Long>, VoteJdslRepository {
}

interface VoteJdslRepository {
    fun updateVote(voteId: Long, req: VoteRequestDto): Query
}

class VoteJdslRepositoryImpl(
    private val queryFactory: SpringDataQueryFactory,
): VoteJdslRepository {
    override fun updateVote(voteId: Long, req: VoteRequestDto): Query {
        return queryFactory.updateQuery<Vote> {
            where(col(Vote::id).equal(voteId))
            setParams(col(Vote::status) to Status.valueOf(req.status))
            setParams(col(Vote::updatedAt) to LocalDateTime.now())
        }
    }
}