package com.fcdebug.earlysoccerserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class EarlysoccerServerApplication

fun main(args: Array<String>) {
	runApplication<EarlysoccerServerApplication>(*args)
}
