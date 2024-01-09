package pl.dlsoftware.app.service

import org.springframework.stereotype.Service
import pl.dlsoftware.app.dto.RepositoryBranch
import pl.dlsoftware.app.dto.UserRepositoriesResponse
import pl.dlsoftware.app.dto.UserRepository
import reactor.core.publisher.Flux

@Service
class GitHubService(
    private val gitHubHttpClient: GitHubHttpClient
) {

    fun getRepositoriesByUsernameNoForks(username: String): UserRepositoriesResponse {
        val userRepositoriesNames = gitHubHttpClient.getRepositories(username)
            .block()
            ?.filter { !it.fork }
            ?.map { it.name } ?: emptyList()

        val userRepositories = Flux.fromIterable(userRepositoriesNames)
            .flatMap { repository ->
                gitHubHttpClient.getBranches(username, repository)
                    .map { branches -> UserRepository(repository,
                        branches.map { branch -> RepositoryBranch(branch.name, branch.commit.sha) }
                    )}
            }
            .collectList()
            .block()

        return UserRepositoriesResponse(username, userRepositories ?: emptyList())
    }
}