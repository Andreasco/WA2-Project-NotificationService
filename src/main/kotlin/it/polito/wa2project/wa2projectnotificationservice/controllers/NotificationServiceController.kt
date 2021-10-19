package it.polito.wa2project.wa2projectnotificationservice.controllers

import it.polito.wa2project.wa2projectnotificationservice.dto.SendEmailBodyDTO
import it.polito.wa2project.wa2projectnotificationservice.services.MailService
import it.polito.wa2project.wa2projectnotificationservice.services.NotificationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotNull

@RestController
@RequestMapping("/notification")
@Validated
class NotificationServiceController(
    val notificationService: NotificationService,
    val mailService: MailService
) {
    @PostMapping("/createToken")
    fun createToken(
        @RequestBody
        @NotNull(message = "Username is required")
        username: String
    ): ResponseEntity<String> {
        val emailVerificationToken = notificationService.createEmailVerificationToken(username)

        return ResponseEntity(emailVerificationToken.getToken(), HttpStatus.OK)
    }

    @PostMapping("/sendEmail")
    fun sendEmail(
        @RequestBody
        @Valid
        bodyDTO: SendEmailBodyDTO
    ): ResponseEntity<String> {
        // These are not null for sure because otherwise a validation error would rise
        val destinationAddress = bodyDTO.destinationAddress!!
        val subject = bodyDTO.subject!!
        val emailText = bodyDTO.text!!

        mailService.sendMessage(destinationAddress, subject, emailText)
        val response = "Email correctly sent"

        return ResponseEntity(response, HttpStatus.OK)
    }

    @GetMapping("/validateToken") //oppure getUsername?
    fun validateToken(
        @RequestParam
        @NotNull(message = "'token' param is required")
        token: String? = null
    ): ResponseEntity<String> {
        val username = notificationService.getUsernameFromEmailVerificationToken(token!!) //Here it's not null for sure otherwise an exception would rise
        //If the token doesn't exist or it's not valid the method above would rise an exception, how to comunicate this to the caller?

        return ResponseEntity(username, HttpStatus.OK)
    }
}
