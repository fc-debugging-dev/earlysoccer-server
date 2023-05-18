package com.fcdebug.earlysoccerserver

import com.fcdebug.earlysoccerserver.domain.member.Member
import com.fcdebug.earlysoccerserver.domain.member.MemberRepository
import com.fcdebug.earlysoccerserver.domain.team.Notification
import com.fcdebug.earlysoccerserver.domain.team.NotificationRepository
import com.fcdebug.earlysoccerserver.domain.team.Team
import com.fcdebug.earlysoccerserver.domain.team.TeamRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@DataJpaTest
class NotificationTest @Autowired constructor(
    private val memberRepository: MemberRepository,
    private val teamRepository: TeamRepository,
    private val notificationRepository: NotificationRepository,
){
    @Test
    fun `팀 공지사항을 생성한다`() {
        // given
        val member = memberRepository.save(
            Member(email = "abc@gmail.com", password = "password", name = "faker", nickname = "faker")
        )
        
        val team = teamRepository.save(
            Team(name = "shooting")
        )
        val notificationTitle = "이번주 경기는 토요일입니다."
        val notificationContent = "늦지않게 오세요."
        
        // when
        val notification = notificationRepository.save(Notification.create(
            title = notificationTitle,
            content = notificationContent,
            team = team,
            writer = member,
        ))
        
        // then
        assertThat(notification.title).isEqualTo(notificationTitle)
        assertThat(notification.content).isEqualTo(notificationContent)
        assertThat(notification.team.name).isEqualTo("shooting")
        assertThat(notification.writer.email).isEqualTo("abc@gmail.com")
        assertThat(notification.writer.name).isEqualTo("faker")
    }
}