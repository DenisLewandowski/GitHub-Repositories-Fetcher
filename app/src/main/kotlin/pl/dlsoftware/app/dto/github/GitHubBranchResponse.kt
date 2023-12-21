package pl.dlsoftware.app.dto.github

data class GitHubBranchResponse(
    val name: String,
    val commit: GitHubCommitResponse
)