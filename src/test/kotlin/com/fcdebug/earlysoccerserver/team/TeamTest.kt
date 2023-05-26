package com.fcdebug.earlysoccerserver.team

import com.fcdebug.earlysoccerserver.domain.team.Team
import com.fcdebug.earlysoccerserver.domain.team.TeamRepository
import io.github.serpro69.kfaker.faker
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@DataJpaTest
class TeamTest @Autowired constructor (
    private val teamRepository: TeamRepository,
) {
    val faker = faker {  }

    @Test
    fun `팀 생성 API`() {
        //given
        val name: String = faker.team.name()
        val teamImg: String = "Test Profile Image"

        //when
        val team = teamRepository.save(
            Team.create(name, teamImg)
        )

        //then
        assertThat(team.name).isEqualTo(name)
        assertThat(team.teamImg).isEqualTo(teamImg)
    }
}