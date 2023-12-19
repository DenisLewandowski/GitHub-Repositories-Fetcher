package pl.dlsoftware.githubrepoapp.controller

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.dlsoftware.githubrepoapp.dto.ErrorResponse
import pl.dlsoftware.githubrepoapp.dto.UserRepositoriesResponse
import pl.dlsoftware.githubrepoapp.exception.GitHubHttpRequestFailedException
import pl.dlsoftware.githubrepoapp.service.GitHubService

@RestController
@RequestMapping("/api/v1/github")
class GithubController(
    private val gitHubService: GitHubService
) {

    @GetMapping("/repos/{username}")
    fun getUserGithubRepositories(@PathVariable username: String): ResponseEntity<UserRepositoriesResponse> {
        return ResponseEntity.ok(gitHubService.getRepositoriesByUsernameNoForks(username))
    }

    @ExceptionHandler
    fun handleGitHubHttpRequestFailedException(exception: GitHubHttpRequestFailedException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(exception.statusCode)
            .body(ErrorResponse(exception.statusCode, exception.errorMessage))
    }

    @ExceptionHandler
    fun handleHttpMediaTypeNotAcceptableException(exception: HttpMediaTypeNotAcceptableException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(exception.statusCode)
            .contentType(MediaType.APPLICATION_JSON)
            .body(ErrorResponse(exception.statusCode.value(), exception.message))
    }
}