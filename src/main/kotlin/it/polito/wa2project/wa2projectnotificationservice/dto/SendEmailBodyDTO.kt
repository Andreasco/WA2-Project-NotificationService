package it.polito.wa2project.wa2projectnotificationservice.dto

import javax.validation.constraints.NotNull

class SendEmailBodyDTO {
    @field:NotNull(message = "Destination address is required")
    var destinationAddress: String? = null

    @field:NotNull(message = "Subject field is required")
    var subject: String? = null

    @field:NotNull(message = "Email text is required")
    var text: String? = null
}
