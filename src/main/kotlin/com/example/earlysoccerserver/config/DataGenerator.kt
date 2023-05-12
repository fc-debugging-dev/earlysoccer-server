package com.example.earlysoccerserver.config

import com.example.earlysoccerserver.domain.member.Member
import com.example.earlysoccerserver.domain.member.MemberRepository
import io.github.serpro69.kfaker.faker
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener

@Configuration
class DataGenerator(
    private val memberRepository: MemberRepository
) {
    val faker = faker {  }

    @EventListener(ApplicationReadyEvent::class)
    private fun generateDummy() {
        memberRepository.save(
            Member(
                email = faker.internet.email(),
                password = "1234",
                name = faker.name.name(),
                nickname = faker.name.firstName(),
                profileImg = faker.internet.slug()
            )
        )
    }
}