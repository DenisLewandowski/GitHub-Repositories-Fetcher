package pl.dlsoftware.githubrepoapp.dto

data class UserRepository(
    val name: String,
    val branches: List<RepositoryBranch>
)
