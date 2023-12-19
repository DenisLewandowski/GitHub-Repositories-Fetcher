package pl.dlsoftware.githubrepoapp.service

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import org.springframework.stereotype.Component
import pl.dlsoftware.githubrepoapp.config.GitHubConfigurationProperties
import pl.dlsoftware.githubrepoapp.dto.GitHubRepositoryResponse

@Component
class GitHubHttpClient(
    private val githubConfig: GitHubConfigurationProperties
) {

    private val httpClient = HttpClient.newHttpClient()
    private val mapper = jacksonObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    fun getRepositoriesForUsername(username: String): List<GitHubRepositoryResponse> {
        val response = httpClient.send(HttpRequest.newBuilder()
            .uri(URI.create("${githubConfig.host}/users/$username/repos"))
            .GET()
            .headers(*createHeader())
            .build(), HttpResponse.BodyHandlers.ofString()
        )

        return mapper.readValue(response.body())
    }

    private fun createHeader(): Array<String> {
        return arrayOf(
            "Authorization", "Bearer ${githubConfig.token}",
            "X-GitHub-Api-Version", githubConfig.version
        )
    }
}