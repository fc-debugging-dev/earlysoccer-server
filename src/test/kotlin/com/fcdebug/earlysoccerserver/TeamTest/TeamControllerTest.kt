package com.fcdebug.earlysoccerserver

import com.fcdebug.earlysoccerserver.controller.TeamController
import com.fcdebug.earlysoccerserver.domain.team.Team
import com.fcdebug.earlysoccerserver.domain.team.TeamRepository
import com.fcdebug.earlysoccerserver.service.TeamService
import io.github.serpro69.kfaker.faker
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

@WebMvcTest(TeamController::class)
class TeamControllerTest @Autowired constructor (
    private val mvc: MockMvc,
) {
    private val faker = faker {  }

    @MockBean
    lateinit var teamService: TeamService

    @MockBean
    lateinit var teamRepository: TeamRepository

    @Test
    fun `팀 스케줄을 가져오는 API`() {
        println("-----------------------------------------")
        mvc.perform(MockMvcRequestBuilders.get("/team/1/schedules")).andDo {
            print(it.response.status)
        }
        println("-----------------------------------------")
        // given
        val team: Team = teamRepository.save(
            Team.create(faker.team.name())
        )

//        //when
//        val schedules = mutableListOf<Schedule>()
//        for (i in 0..1) {
//            val schedule: Schedule = Schedule.create(
//                team = team,
//                date = LocalDateTime.now(),
//                place = faker.address.fullAddress(),
//                opponent = faker.team.name(),
//            )
//            schedules.add(schedule)
//        }
//        scheduleRepository.saveAll(schedules)
//        val saveSchedules: ResultActions =
//            mvc.perform(MockMvcRequestBuilders.get("/team/${team.toDto().id}/schedules"))
//        print(saveSchedules)
    }
}