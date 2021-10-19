package it.polito.wa2project.wa2projectnotificationservice.repositories

import it.polito.wa2project.wa2projectnotificationservice.domain.EmailVerificationToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.sql.Timestamp

@Repository
interface EmailVerificationTokenRepository: JpaRepository<EmailVerificationToken, Long> {
    fun findByUsername(username: String): EmailVerificationToken?

    fun findByToken(token: String): EmailVerificationToken?

    fun findByExpiryDateBefore(currentTimestamp: Timestamp): Set<EmailVerificationToken>
}
