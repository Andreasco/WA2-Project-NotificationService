package it.polito.wa2project.wa2projectnotificationservice.services

import it.polito.wa2project.wa2projectnotificationservice.domain.EmailVerificationToken
import it.polito.wa2project.wa2projectnotificationservice.exceptions.NotFoundException
import it.polito.wa2project.wa2projectnotificationservice.exceptions.TokenExpiredException
import it.polito.wa2project.wa2projectnotificationservice.repositories.EmailVerificationTokenRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp

@Service
@Transactional
class NotificationService(val emailVerificationTokenRepository: EmailVerificationTokenRepository) {
    fun createEmailVerificationToken(username: String): EmailVerificationToken {
        val emailVerificationToken = EmailVerificationToken(username)

        return emailVerificationTokenRepository.saveAndFlush(emailVerificationToken)
    }

    /**
     * Returns the username associated to the token.
     * It also verifies if the token exists and it's not expired.
     *
     * @return the username associated to the token
     * @throws NotFoundException if the token doesn't exist
     * @throws TokenExpiredException if the token is expired
     */
    fun getUsernameFromEmailVerificationToken(token: String): String {
        val emailVerificationToken = emailVerificationTokenRepository.findByToken(token)
            ?: throw NotFoundException("Token not found")

        if (emailVerificationToken.expiryDate.before(Timestamp(System.currentTimeMillis())))
            throw TokenExpiredException("Token is expired")

        return emailVerificationToken.username
    }

    /**
     * These methods are used by [ExpiredTokensCleaner] to get expired tokens and delete them
     */

    fun getExpiredTokens(): Set<EmailVerificationToken> =
        emailVerificationTokenRepository.findByExpiryDateBefore(Timestamp(System.currentTimeMillis()))

    fun deleteTokenById(id: Long) =
        emailVerificationTokenRepository.deleteById(id)
}
