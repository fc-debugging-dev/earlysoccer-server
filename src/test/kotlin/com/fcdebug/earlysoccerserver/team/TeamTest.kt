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
    private val teamService: TeamService,
) {
    val faker = faker {  }
    @Test
    @Transactional
    fun `팀 스케줄을 가져온다`() {
        //given
        val team: Team = teamRepository.save(
            Team.create(faker.team.name())
        )

        //when
        val schedules = mutableListOf<Schedule>()
        for (i in 0..1) {
            val schedule: Schedule = Schedule.create(
                team = team,
                date = LocalDateTime.now(),
                place = faker.address.fullAddress(),
                opponent = faker.team.name(),
            )
            schedules.add(schedule)
        }
        scheduleRepository.saveAll(schedules)
        val saveSchedules: List<ScheduleDto> = teamService.findTeamSchedules(team.toDto().id!!)

        //then
        for (i in 0..1) {
            assertThat(saveSchedules[i].team.name).isEqualTo(schedules[i].team.name)
            assertThat(saveSchedules[i].date).isEqualTo(schedules[i].date)
            assertThat(saveSchedules[i].place).isEqualTo(schedules[i].place)
            assertThat(saveSchedules[i].opponent).isEqualTo(schedules[i].opponent)
        }
    }
}