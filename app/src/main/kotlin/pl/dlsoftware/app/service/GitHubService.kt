package pl.dlsoftware.app.service

import org.springframework.stereotype.Service
import pl.dlsoftware.app.dto.RepositoryBranch
import pl.dlsoftware.app.dto.UserRepositoriesResponse
import pl.dlsoftware.app.dto.UserRepository

@Service
class GitHubService(
    private val gitHubHttpClient: GitHubHttpClient
) {

    fun getRepositoriesByUsernameNoForks(username: String): UserRepositoriesResponse {
        val userRepositories = gitHubHttpClient.getRepositories(username)
            .filter { !it.fork }
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