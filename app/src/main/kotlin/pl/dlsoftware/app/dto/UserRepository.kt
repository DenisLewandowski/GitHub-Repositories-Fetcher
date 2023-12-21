package pl.dlsoftware.app.dto

data class UserRepository(
    val name: String,
    val branches: List<RepositoryBranch>
)
