package it.polito.wa2project.wa2projectnotificationservice.services

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.slf4j.Logger
import org.slf4j.LoggerFactory


@Service
class ExpiredTokensCleaner(val notificationService: NotificationService) {
    private val logger: Logger = LoggerFactory.getLogger(ExpiredTokensCleaner::class.java)

    /**
     * Delete expired tokens in the DB.
     * It runs every 15 minutes since a token lasts 30 minutes, so 30/2 = 15
     */
    @Scheduled(fixedRate = 900000)
    fun cleanExpiredTokens(){
        val expiredTokens = notificationService.getExpiredTokens()
        expiredTokens.forEach { notificationService.deleteTokenById(it.getId()!!) } // It will have an ID for sure because it comes from the DB

        logger.info("Cleaned expired tokens :: Deleted {} expired tokens", expiredTokens.size)
    }
}
