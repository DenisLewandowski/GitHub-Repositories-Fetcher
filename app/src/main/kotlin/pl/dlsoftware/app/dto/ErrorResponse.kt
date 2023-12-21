package pl.dlsoftware.app.dto

data class ErrorResponse(
    val status: Int,
    val message: String?
)