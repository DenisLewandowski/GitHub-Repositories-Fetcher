package pl.dlsoftware.githubrepoapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GithubRepoApplication

fun main(args: Array<String>) {
	runApplication<GithubRepoApplication>(*args)
}
