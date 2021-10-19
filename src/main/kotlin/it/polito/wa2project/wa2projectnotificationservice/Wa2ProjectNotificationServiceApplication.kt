package it.polito.wa2project.wa2projectnotificationservice

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.Bean
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableEurekaClient
@EnableScheduling // This enables scheduled tasks such as the one in the ExpiredTokensCleaner
class Wa2ProjectNotificationServiceApplication{
    @Value("\${spring.mail.host}")
    private val host: String? = null

    @Value("\${spring.mail.port}")
    private val port: Int? = null

    @Value("\${spring.mail.username}")
    private val username: String? = null

    @Value("\${spring.mail.password}")
    private val password: String? = null

    @Value("\${spring.mail.properties.mail.smtp.auth}")
    private val mailSmtpAuth: String? = null

    @Value("\${spring.mail.properties.mail.smtp.starttls.enable}")
    private val mailSmtpStartTlsEnable: String? = null

    @Value("\${spring.mail.properties.mail.debug}")
    private val mailDebug: String? = null

    @Bean
    fun getMailSender(): JavaMailSender {
        val mailSender = JavaMailSenderImpl()
        mailSender.host = host
        mailSender.port = port ?: 587
        mailSender.username = username
        mailSender.password = password
        mailSender.javaMailProperties["mail.smtp.auth"] = mailSmtpAuth
        mailSender.javaMailProperties["mail.smtp.starttls.enable"] = mailSmtpStartTlsEnable
        mailSender.javaMailProperties["mail.debug"] = mailDebug

        return mailSender
    }
}

fun main(args: Array<String>) {
    runApplication<Wa2ProjectNotificationServiceApplication>(*args)
}
