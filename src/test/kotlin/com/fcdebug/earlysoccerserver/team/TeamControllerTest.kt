package com.fcdebug.earlysoccerserver.team

import com.fcdebug.earlysoccerserver.controller.TeamController
import com.fcdebug.earlysoccerserver.controller.request.ScheduleRequestDto
import com.fcdebug.earlysoccerserver.controller.request.TeamRequestDto
import com.fcdebug.earlysoccerserver.controller.response.ScheduleResponseDto
import com.fcdebug.earlysoccerserver.domain.team.Team
import com.fcdebug.earlysoccerserver.domain.team.TeamDto
import com.fcdebug.earlysoccerserver.service.TeamService
import io.github.serpro69.kfaker.faker
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@ActiveProfiles("test")
@WebMvcTest(TeamController::class)
@WithMockUser
class TeamControllerTest @Autowired constructor (
    private val mvc: MockMvc,
) {
    private val faker = faker {  }
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    @MockBean
    lateinit var teamService: TeamService

    @Test
    fun `팀을 생성하는 API`() {
        val teamName: String = faker.team.name()
        val teamImg = "Test Profile Image"
        val req = TeamRequestDto(teamName, teamImg)
        given(teamService.createTeam(req)).willReturn(
            TeamDto.toDto(Team.create(teamName, teamImg))
        )
        mvc.perform(post("/team")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(req.toString()).with(csrf()))
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.name").value(teamName))
            .andExpect(jsonPath("\$.teamImg").value(teamImg))
    }

    @Test
    fun `팀 전체 스케줄을 가져오는 API`() {
        val now: String = LocalDateTime.now().format(formatter)
        val localDateTimeNow = LocalDateTime.parse(now, formatter)
        val place: String = faker.address.fullAddress()
        val schedules = listOf(ScheduleResponseDto(
            1, localDateTimeNow, place, "Test opponent", "Test Note"))
        given(teamService.findTeamSchedules(1)).willReturn(schedules)
        mvc.perform(get("/team/1/schedules"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.[0].id").value(1))
            .andExpect(jsonPath("\$.[0].date").value(localDateTimeNow.toString()))
            .andExpect(jsonPath("\$.[0].place").value(place))
            .andExpect(jsonPath("\$.[0].opponent").value("Test opponent"))
            .andExpect(jsonPath("\$.[0].note").value("Test Note"))
    }

    @Test
    fun `팀 스케줄을 생성하는 API`() {
        val now: String = LocalDateTime.now().format(formatter)
        val localDateTimeNow = LocalDateTime.parse(now, formatter)
        val place: String = faker.address.fullAddress()
        val opponent = "Test opponent"
        val note = "Test Note"
        val req = ScheduleRequestDto(
            date = localDateTimeNow,
            place = place,
            opponent = opponent,
            note = note,
        )
        given(teamService.createTeamSchedules(1, req)).willReturn(
            ScheduleResponseDto(1, localDateTimeNow, place, opponent, note)
        )
        mvc.perform(post("/team/1/schedules")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(req.toString()).with(csrf()))
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.id").value(1))
            .andExpect(jsonPath("\$.date").value(localDateTimeNow.toString()))
            .andExpect(jsonPath("\$.place").value(place))
            .andExpect(jsonPath("\$.opponent").value(opponent))
            .andExpect(jsonPath("\$.note").value(note))
    }

    @Test
    fun `팀 스케줄을 수정하는 API`() {
        val now: String = LocalDateTime.now().format(formatter)
        val localDateTimeNow = LocalDateTime.parse(now, formatter)
        val place: String = faker.address.fullAddress()
        val opponent = "Test opponent"
        val note = "Test Note"
        val req = ScheduleRequestDto(
            date = localDateTimeNow,
            place = place,
            opponent = opponent,
            note = note,
        )
        given(teamService.updateTeamSchedules(1, 1, req)).willReturn(
            ScheduleResponseDto(1, localDateTimeNow, place, opponent, note)
        )
        mvc.perform(put("/team/1/schedules/1")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(req.toString()).with(csrf()))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.id").value(1))
            .andExpect(jsonPath("\$.date").value(localDateTimeNow.toString()))
            .andExpect(jsonPath("\$.place").value(place))
            .andExpect(jsonPath("\$.opponent").value("Test opponent"))
            .andExpect(jsonPath("\$.note").value("Test Note"))
    }
}