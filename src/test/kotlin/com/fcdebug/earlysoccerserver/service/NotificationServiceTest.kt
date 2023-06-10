package com.fcdebug.earlysoccerserver.service

import com.fcdebug.earlysoccerserver.controller.exception.CustomException
import com.fcdebug.earlysoccerserver.controller.exception.ErrorCode
import com.fcdebug.earlysoccerserver.domain.member.Member
import com.fcdebug.earlysoccerserver.domain.member.MemberRepository
import com.fcdebug.earlysoccerserver.domain.team.Notification
import com.fcdebug.earlysoccerserver.domain.team.NotificationRepository
import com.fcdebug.earlysoccerserver.domain.team.Team
import com.fcdebug.earlysoccerserver.domain.team.TeamRepository
import com.fcdebug.earlysoccerserver.service.request.CreateNotificationServiceRequestDto
import com.fcdebug.earlysoccerserver.service.request.UpdateNotificationServiceRequestDto
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.groups.Tuple
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional


@Transactional
@ActiveProfiles("test")
@SpringBootTest
class NotificationServiceTest @Autowired constructor(
    val notificationRepository: NotificationRepository,
    val teamRepository: TeamRepository,
    val memberRepository: MemberRepository,
    val notificationService: NotificationService,
) {
    
    @Test
    fun `팀의 전체 공지사항을 조회한다`() {
        // given
        val team = teamRepository.save(Team.create(name = "shooting"))
        
        val member = memberRepository.save(Member(
            email = "test@gmail.com",
            password = "sdfsdf",
            name = "test",
            nickname = "test",
        ))
        
        val notification1 = createNotification("1", "test1", member, team)
        val notification2 = createNotification("2", "test2", member, team)
        val notification3 = createNotification("3", "test3", member, team)
        
        notificationRepository.saveAll(arrayListOf(notification1, notification2, notification3))
        
        // when
        val result = notificationService.findTeamNotifications(team.id!!)
        
        // then
        assertThat(result).hasSize(3)
            .extracting("title", "writer")
            .containsExactlyInAnyOrder(
                Tuple.tuple("1", "test"),
                Tuple.tuple("2", "test"),
                Tuple.tuple("3", "test"),
            )
    }
    
    @Test
    fun `팀에 등록된 공지사항이 없는 경우는 빈 리스트로 조회된다`() {
        // given
        val team = teamRepository.save(Team.create(name = "shooting"))
        
        val member = memberRepository.save(Member(
            email = "test@gmail.com",
            password = "sdfsdf",
            name = "test",
            nickname = "test",
        ))
        
        // when
        val result = notificationService.findTeamNotifications(team.id!!)
        
        // then
        assertThat(result).isEmpty()
    }
    
    @Test
    fun `팀 공지사항을 수정한다`() {
        // given
        val team = teamRepository.save(Team.create(name = "shooting"))
        
        val member = memberRepository.save(Member(
            email = "test@gmail.com",
            password = "sdfsdf",
            name = "test",
            nickname = "test",
        ))
        
        val notification = notificationRepository.save(createNotification("1", "test1", member, team))
        val notificationRequestDto = UpdateNotificationServiceRequestDto(
            id = notification.id!!,
            title = "Updated Title",
            content = "testtest"
        )
        
        // when
        val result = notificationService.updateNotificationById(notificationRequestDto)
        
        // then
        val updatedNotification = notificationRepository.findById(result.id).get()
        
        assertThat(updatedNotification.title).isEqualTo(notificationRequestDto.title)
        assertThat(updatedNotification.content).isEqualTo(notificationRequestDto.content)
    }
    
    @Test
    fun `팀 공지사항을 등록한다`() {
        // given
        val team = teamRepository.save(Team.create(name = "shooting"))
        
        val member = memberRepository.save(Member(
            email = "test@gmail.com",
            password = "sdfsdf",
            name = "test",
            nickname = "test",
        ))
        
        val createRequestDto = CreateNotificationServiceRequestDto(
            title = "test",
            content = "test",
            memberId = member.id!!,
            teamId = team.id!!,
        )
        
        // when
        val createNotification = notificationService.createNotification(createRequestDto)
        
        // then
        val notification = notificationRepository.findById(createNotification.id).get()
        
        assertThat(notification.title).isEqualTo(createRequestDto.title)
        assertThat(notification.content).isEqualTo(createRequestDto.content)
        assertThat(notification.writer.name).isEqualTo(member.name)
        assertThat(notification.team.name).isEqualTo(team.name)
    }
    
    @Test
    fun `존재하지 않는 공지사항을 삭제하려는 경우 예외가 발생한다`() {
        
        assertThatThrownBy {
            notificationService.deleteNotificationById(0)
        }.isInstanceOf(CustomException::class.java)
            .extracting("errorCode")
            .isEqualTo(ErrorCode.NOTIFICATION_NOT_FOUND)
    }
    
    private fun createNotification(title: String, content: String, writer: Member, team: Team) =
        Notification(
            title = title,
            content = content,
            writer = writer,
            team = team,
        )
}
