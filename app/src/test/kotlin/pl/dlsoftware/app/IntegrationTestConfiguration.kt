package pl.dlsoftware.app

import org.mockito.Mockito
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import pl.dlsoftware.app.dto.github.GitHubBranchResponse
import pl.dlsoftware.app.dto.github.GitHubCommitResponse
import pl.dlsoftware.app.dto.github.GitHubRepositoryResponse
import pl.dlsoftware.app.exception.GitHubHttpRequestFailedException
import pl.dlsoftware.app.service.GitHubHttpClient
import reactor.core.publisher.Mono

@Configuration
class IntegrationTestConfiguration {

    @Bean
    @Primary
    fun gitHubHttpClient(): GitHubHttpClient {
        val httpClientMock = Mockito.mock(GitHubHttpClient::class.java)
        Mockito.`when`(httpClientMock.getRepositories("notexistingusername")).thenThrow(GitHubHttpRequestFailedException(404, "Not Found"))
        Mockito.`when`(httpClientMock.getRepositories("username")).thenReturn(Mono.just(listOf(
            GitHubRepositoryResponse("repo1", false),
            GitHubRepositoryResponse("repo2", false),
            GitHubRepositoryResponse("repo3", true),
        )))
        Mockito.`when`(httpClientMock.getBranches("username", "repo1")).thenReturn(Mono.just(listOf(
            GitHubBranchResponse("main", GitHubCommitResponse("commitsha1")),
            GitHubBranchResponse("develop", GitHubCommitResponse("commitsha1")),
        )))
        Mockito.`when`(httpClientMock.getBranches("username", "repo2")).thenReturn(Mono.just(listOf(
            GitHubBranchResponse("main", GitHubCommitResponse("commitsha2")),
            GitHubBranchResponse("develop", GitHubCommitResponse("commitsha2")),
        )))
        return httpClientMock
    }
}