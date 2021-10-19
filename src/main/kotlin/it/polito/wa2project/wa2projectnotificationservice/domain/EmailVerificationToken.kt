package it.polito.wa2project.wa2projectnotificationservice.domain

import java.sql.Timestamp
import java.util.*
import java.util.concurrent.TimeUnit
import javax.persistence.Entity
import javax.validation.constraints.NotNull

@Entity
class EmailVerificationToken(
    @NotNull
    var username: String,

    var expiryDate: Timestamp = // By default, it expires in 30 minutes
        Timestamp(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30)),

    private var token: String = UUID.randomUUID().toString()
): EntityBase<Long>() {

    fun getToken(): String = token
}
