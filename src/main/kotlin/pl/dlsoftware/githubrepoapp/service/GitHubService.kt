package pl.dlsoftware.githubrepoapp.service

import org.springframework.stereotype.Service
import pl.dlsoftware.githubrepoapp.dto.RepositoryBranch
import pl.dlsoftware.githubrepoapp.dto.UserRepositoriesResponse
import pl.dlsoftware.githubrepoapp.dto.UserRepository

@Service
class GitHubService(
    private val gitHubHttpClient: GitHubHttpClient
) {

    fun getRepositoriesByUsernameNoForks(username: String): UserRepositoriesResponse {
        val userRepositories = gitHubHttpClient.getRepositories(username)
            .parallelStream()
            .map { repository ->
                val branches = gitHubHttpClient.getBranches(username, repository.name)
                    .map { RepositoryBranch(it.name, it.commit.sha) }
                UserRepository(repository.name, branches)
            }
            .toList()

        return UserRepositoriesResponse(username, userRepositories)
    }

}