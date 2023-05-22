package com.fcdebug.earlysoccerserver.team

import com.fcdebug.earlysoccerserver.domain.schedule.Schedule
import com.fcdebug.earlysoccerserver.domain.schedule.ScheduleDto
import com.fcdebug.earlysoccerserver.domain.schedule.ScheduleRepository
import com.fcdebug.earlysoccerserver.domain.team.Team
import com.fcdebug.earlysoccerserver.domain.team.TeamRepository
import com.fcdebug.earlysoccerserver.service.TeamService
import io.github.serpro69.kfaker.faker
import jakarta.transaction.Transactional
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDateTime

@ActiveProfiles("test")
@SpringBootTest
class TeamTest @Autowired constructor (
    private val teamRepository: TeamRepository,
    private val scheduleRepository: ScheduleRepository,
) {
    val faker = faker {  }
    @Test
    @Transactional
    fun `팀의 전체 스케줄을 가져온다`() {
        //given
        val team: Team = teamRepository.save(
            Team.create(faker.team.name())
        )

        //when
        val schedules = listOf(
            Schedule.create(
                team = team,
                date = LocalDateTime.now(),
                place = faker.address.fullAddress(),
                opponent = faker.team.name(),))
        scheduleRepository.saveAll(schedules)
        val saveSchedules: List<Schedule> = scheduleRepository.findByTeamId(team.toDto().id!!)

        //then
        assertThat(saveSchedules[0].team.name).isEqualTo(schedules[0].team.name)
        assertThat(saveSchedules[0].date).isEqualTo(schedules[0].date)
        assertThat(saveSchedules[0].place).isEqualTo(schedules[0].place)
        assertThat(saveSchedules[0].opponent).isEqualTo(schedules[0].opponent)
    }
}