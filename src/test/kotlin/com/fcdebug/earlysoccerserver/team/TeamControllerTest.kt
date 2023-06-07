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
import org.springframework.util.LinkedMultiValueMap
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
    fun `팀의 특정 기간 스케줄을 가져오는 API`() {
        val localDateTimeNow: LocalDateTime = LocalDateTime.now()
        val stringNow: String = localDateTimeNow.format(formatter)
        val stringTomorrow: String = localDateTimeNow.plusDays(1).format(formatter)
        val place: String = faker.address.fullAddress()
        val opponent: String = faker.team.name()
        val note = "Test Note"
        val schedules = listOf(ScheduleResponseDto(1, localDateTimeNow, place, opponent, note))
        val parameter = LinkedMultiValueMap<String, String>()
        parameter.add("start", stringNow)
        parameter.add("end", stringTomorrow)
        given(teamService.findTeamSchedules(1, start=stringNow, end=stringTomorrow)).willReturn(schedules)
        mvc.perform(get("/team/1/schedules").params(parameter))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.[0].id").value(1))
            .andExpect(jsonPath("\$.[0].date").value(localDateTimeNow.toString()))
            .andExpect(jsonPath("\$.[0].place").value(place))
            .andExpect(jsonPath("\$.[0].opponent").value(opponent))
            .andExpect(jsonPath("\$.[0].note").value(note))
    }

    @Test
    fun `팀의 월단위 스케줄을 가져오는 API`() {
        val localDateTimeNow: LocalDateTime = LocalDateTime.now()
        val place: String = faker.address.fullAddress()
        val opponent: String = faker.team.name()
        val note = "Test Note"
        val schedules = listOf(ScheduleResponseDto(1, localDateTimeNow, place, opponent, note))
        val parameter = LinkedMultiValueMap<String, String>()
        parameter.add("year", "2023")
        parameter.add("month", "06")
        given(teamService.findTeamSchedules(1, year="2023", month="06")).willReturn(schedules)
        mvc.perform(get("/team/1/schedules").params(parameter))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.[0].id").value(1))
            .andExpect(jsonPath("\$.[0].date").value(localDateTimeNow.toString()))
            .andExpect(jsonPath("\$.[0].place").value(place))
            .andExpect(jsonPath("\$.[0].opponent").value(opponent))
            .andExpect(jsonPath("\$.[0].note").value(note))
    }

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
        given(teamService.updateTeamSchedules(1, req)).willReturn(
            ScheduleResponseDto(1, localDateTimeNow, place, opponent, note, mutableListOf(), mutableListOf())
        )
        mvc.perform(put("/team/schedules/1")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(req.toString()).with(csrf()))
            .andExpect(status().isOk)
            .andExpect(jsonPath("\$.id").value(1))
            .andExpect(jsonPath("\$.date").value(localDateTimeNow.toString()))
            .andExpect(jsonPath("\$.place").value(place))
            .andExpect(jsonPath("\$.opponent").value(opponent))
            .andExpect(jsonPath("\$.note").value(note))
    }
}