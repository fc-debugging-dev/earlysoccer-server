package com.fcdebug.earlysoccerserver.team

import com.fcdebug.earlysoccerserver.controller.TeamController
import com.fcdebug.earlysoccerserver.domain.schedule.ScheduleDto
import com.fcdebug.earlysoccerserver.domain.team.Team
import com.fcdebug.earlysoccerserver.service.TeamService
import io.github.serpro69.kfaker.faker
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import java.time.LocalDateTime

@ActiveProfiles("test")
@WebMvcTest(TeamController::class)
@WithMockUser
class TeamControllerTest @Autowired constructor (
    private val mvc: MockMvc,
) {
    private val faker = faker {  }

    @MockBean
    lateinit var teamService: TeamService

    @Test
    fun `팀 전체 스케줄을 가져오는 API`() {
        val team: Team = Team("Test team")
        val now: LocalDateTime = LocalDateTime.now()
        val place: String = faker.address.fullAddress()
        val schedule: ScheduleDto = ScheduleDto(1, team, now, place, "Test opponent")
        val schedules: List<ScheduleDto> = listOf(schedule)
        given(teamService.findTeamSchedules(1)).willReturn(schedules)
        mvc.perform(get("/team/1/schedules")).andExpect {
            status().isOk
            content().contentType((MediaType.APPLICATION_JSON))
            jsonPath("\$.[0].id").value(schedule.id)
            jsonPath("\$.[0].team.name").value("Test team")
            jsonPath("\$.[0].date").value(now)
            jsonPath("\$.[0].place").value(place)
            jsonPath("\$.[0].opponent").value("Test opponent")
        }
    }
}