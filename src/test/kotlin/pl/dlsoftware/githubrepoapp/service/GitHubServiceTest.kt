package pl.dlsoftware.githubrepoapp.service

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito
import pl.dlsoftware.githubrepoapp.dto.github.GitHubBranchResponse
import pl.dlsoftware.githubrepoapp.dto.github.GitHubCommitResponse
import pl.dlsoftware.githubrepoapp.dto.github.GitHubRepositoryResponse

internal class GitHubServiceTest {

    val gitHubHttpClient = Mockito.mock(GitHubHttpClient::class.java)
    val gitHubService = GitHubService(gitHubHttpClient)

    @Test
    fun `getRepositoriesByUsernameNoForks - happy path`() {
        val username = "username"
        val responses = createRepositoryResponses()
        Mockito.`when`(gitHubHttpClient.getRepositories(username)).thenReturn(responses)
        Mockito.`when`(gitHubHttpClient.getBranches(username, "repo-1")).thenReturn(listOf(createBranchResponse("repo-1", "sha-1")))
        Mockito.`when`(gitHubHttpClient.getBranches(username, "repo-2")).thenReturn(listOf(createBranchResponse("repo-2", "sha-2")))

        val result = gitHubService.getRepositoriesByUsernameNoForks(username)

        assertEquals(2, result.repositories.size)
        assertEquals(username, result.ownerLogin)
        assertFalse(result.repositories.any { it.name == "repo-3" })
    }

    private fun createRepositoryResponses(): List<GitHubRepositoryResponse> {
        return listOf(
            GitHubRepositoryResponse("repo-1", false),
            GitHubRepositoryResponse("repo-2", false),
            GitHubRepositoryResponse("repo-3", true)
        )
    }

    private fun createBranchResponse(repositoryName: String, sha: String): GitHubBranchResponse {
        return GitHubBranchResponse(repositoryName, GitHubCommitResponse(sha))
    }
}