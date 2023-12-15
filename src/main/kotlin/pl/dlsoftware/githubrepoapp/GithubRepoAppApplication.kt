package pl.dlsoftware.githubrepoapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GithubRepoAppApplication

fun main(args: Array<String>) {
	runApplication<GithubRepoAppApplication>(*args)
}
