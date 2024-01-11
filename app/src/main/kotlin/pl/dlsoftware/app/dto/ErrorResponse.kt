package pl.dlsoftware.app.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ErrorResponse(
    val status: Int,
    @JsonProperty("Message")
    val message: String?
)