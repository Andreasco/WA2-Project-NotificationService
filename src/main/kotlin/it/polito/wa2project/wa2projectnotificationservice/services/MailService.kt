package it.polito.wa2project.wa2projectnotificationservice.services

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class MailService(val javaMailSender: JavaMailSender) {
    fun sendMessage(toMail: String, subject: String, mailBody: String){
        val message = SimpleMailMessage()
        message.setTo(toMail)
        message.setSubject(subject)
        message.setText(mailBody)

        javaMailSender.send(message)
    }
}
