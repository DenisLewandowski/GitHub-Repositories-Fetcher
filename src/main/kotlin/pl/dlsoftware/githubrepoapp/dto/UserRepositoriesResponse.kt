package pl.dlsoftware.githubrepoapp.dto

data class UserRepositoriesResponse(
    val ownerLogin: String,
    val repositories: List<UserRepository>
)