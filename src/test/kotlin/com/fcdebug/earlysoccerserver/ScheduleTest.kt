package com.fcdebug.earlysoccerserver

import com.fcdebug.earlysoccerserver.domain.member.MemberRepository
import com.fcdebug.earlysoccerserver.domain.schedule.Schedule
import com.fcdebug.earlysoccerserver.domain.team.Team
import com.fcdebug.earlysoccerserver.domain.team.TeamRepository
import io.github.serpro69.kfaker.faker
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDateTime

@ActiveProfiles("test")
@DataJpaTest
class ScheduleTest @Autowired constructor(
    private var teamRepository: TeamRepository,
) {
    val faker = faker {  }
    @Test
    fun `팀 스케줄을 생성한다`() {
        //given
        val team: Team = teamRepository.save(
            Team.create(faker.team.name())
        )
        val teamName: String = team.name
        val date: LocalDateTime = LocalDateTime.now()
        val place: String = faker.address.fullAddress()
        val opponent: String = faker.team.name()

        //when
        val schedule: Schedule = Schedule.create(
            team = team,
            date = date,
            place = place,
            opponent = opponent,
        )

        //then
        assertThat(schedule.team.name).isEqualTo(teamName)
        assertThat(schedule.date).isEqualTo(date)
        assertThat(schedule.place).isEqualTo(place)
        assertThat(schedule.opponent).isEqualTo(opponent)
    }
}