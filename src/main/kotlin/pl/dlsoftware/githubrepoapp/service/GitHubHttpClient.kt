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
import pl.dlsoftware.githubrepoapp.dto.github.GitHubBranchResponse
import pl.dlsoftware.githubrepoapp.dto.github.GitHubErrorResponse
import pl.dlsoftware.githubrepoapp.dto.github.GitHubRepositoryResponse
import pl.dlsoftware.githubrepoapp.exception.GitHubHttpRequestFailedException

@Component
class GitHubHttpClient(
    private val githubConfig: GitHubConfigurationProperties
) {

    private val httpClient = HttpClient.newHttpClient()
    private val mapper = jacksonObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    fun getRepositories(username: String): List<GitHubRepositoryResponse> {
        val response = makeGetRequest("/users/$username/repos")
        return mapper.readValue(response.body())
    }

    fun getBranches(username: String, repositoryName: String): List<GitHubBranchResponse> {
        val response = makeGetRequest("/repos/$username/$repositoryName/branches")
        return mapper.readValue(response.body())
    }

    private fun makeGetRequest(endpoint: String): HttpResponse<String> {
        val response = httpClient.send(HttpRequest.newBuilder()
            .uri(URI.create("${githubConfig.host}$endpoint"))
            .GET()
            .headers(*createHeader())
            .build(), HttpResponse.BodyHandlers.ofString()
        )

        if (response.statusCode() != 200) {
            val message = mapper.readValue<GitHubErrorResponse>(response.body()).message
            throw GitHubHttpRequestFailedException(response.statusCode(), message)
        }

        return response
    }

    private fun createHeader(): Array<String> {
        return arrayOf(
            "Authorization", "Bearer ${githubConfig.token}",
            "X-GitHub-Api-Version", githubConfig.version
        )
    }
}