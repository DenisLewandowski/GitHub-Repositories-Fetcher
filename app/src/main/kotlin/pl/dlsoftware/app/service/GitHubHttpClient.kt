package pl.dlsoftware.app.service

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import pl.dlsoftware.app.config.GitHubConfigurationProperties
import pl.dlsoftware.app.dto.github.GitHubBranchResponse
import pl.dlsoftware.app.dto.github.GitHubRepositoryResponse
import reactor.core.publisher.Mono

@Component
class GitHubHttpClient(
    private val githubConfig: GitHubConfigurationProperties
) {

    private val webClient = WebClient.builder()
        .baseUrl(githubConfig.host)
        .defaultHeader("X-GitHub-Api-Version", githubConfig.version)
        .applyBearerTokenIfProvided()
        .build()

    fun getRepositories(username: String): Mono<List<GitHubRepositoryResponse>> {
        return webClient.get()
            .uri("/users/$username/repos")
            .retrieve()
            .bodyToMono()
    }

    fun getBranches(username: String, repositoryName: String): Mono<List<GitHubBranchResponse>> {
        return webClient.get()
            .uri("/repos/$username/$repositoryName/branches")
            .retrieve()
            .bodyToMono()
    }

    private fun WebClient.Builder.applyBearerTokenIfProvided(): WebClient.Builder {
        if (githubConfig.token.isNotEmpty()) {
            this.defaultHeader("Authorization", "Bearer ${githubConfig.token}")
        }
        return this
    }
}