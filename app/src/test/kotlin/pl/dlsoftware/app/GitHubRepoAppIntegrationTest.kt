package pl.dlsoftware.app

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import pl.dlsoftware.app.dto.UserRepositoriesResponse

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class GitHubRepoAppIntegrationTest {

    companion object {
        val httpClient: HttpClient = HttpClient.newHttpClient()
        val getGitHubRepoEndpoint = "http://localhost:8080/api/v1/github/repos"
    }

    @Test
    fun `get GitHub repositories - happy path`() {
        val username = "username"
        val response = HttpClient.newHttpClient().send(HttpRequest.newBuilder()
            .uri(URI("$getGitHubRepoEndpoint/$username"))
            .GET()
            .build(), HttpResponse.BodyHandlers.ofString()
        )

        val repositoriesResponse: UserRepositoriesResponse = jacksonObjectMapper().readValue(response.body())
        assertEquals(username, repositoriesResponse.ownerLogin)
        assertEquals("repo1", repositoriesResponse.repositories[0].name)
        assertEquals("repo2", repositoriesResponse.repositories[1].name)
        assertFalse(repositoriesResponse.repositories.any { it.name == "repo3" })
    }

    @Test
    fun `when the user does not exists - return 404 response code`() {
        val username = "notexistingusername"
        val response = HttpClient.newHttpClient().send(HttpRequest.newBuilder()
            .uri(URI("$getGitHubRepoEndpoint/$username"))
            .GET()
            .build(), HttpResponse.BodyHandlers.ofString()
        )

        assertEquals(404, response.statusCode())
        assertEquals("{\"status\":404,\"message\":\"Not Found\"}", response.body())
    }

    @Test
    fun `when application xml header is passed - return 406 response code`() {
        val username = "username"
        val response = httpClient.send(HttpRequest.newBuilder()
            .uri(URI("$getGitHubRepoEndpoint/$username"))
            .GET()
            .header("Accept", "application/xml")
            .build(), HttpResponse.BodyHandlers.ofString()
        )

        assertEquals(406, response.statusCode())
        assertEquals("{\"status\":406,\"message\":\"No acceptable representation\"}", response.body())
    }
}