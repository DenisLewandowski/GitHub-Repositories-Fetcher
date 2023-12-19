package pl.dlsoftware.githubrepoapp.dto.github

data class GitHubBranchResponse(
    val name: String,
    val commit: GitHubCommitResponse
)