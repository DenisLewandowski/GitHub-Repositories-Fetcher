package pl.dlsoftware.app.dto

data class UserRepositoriesResponse(
    val ownerLogin: String,
    val repositories: List<UserRepository>
)