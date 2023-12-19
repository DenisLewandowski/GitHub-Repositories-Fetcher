package pl.dlsoftware.githubrepoapp

import io.github.cdimascio.dotenv.Dotenv
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.core.env.MapPropertySource
import org.springframework.core.env.StandardEnvironment

@SpringBootApplication
@ConfigurationPropertiesScan
class GithubRepoApplication

const val ENV_FILE_NAME = ".env"

fun main(args: Array<String>) {
	val environment = StandardEnvironment().apply {
		propertySources.addLast(MapPropertySource("dotenvProperties", prepareDotEnvVariables()))
	}

	SpringApplicationBuilder(GithubRepoApplication::class.java)
		.environment(environment)
		.run(*args)
}

private fun prepareDotEnvVariables(): Map<String, Any> {
	return Dotenv.configure()
		.ignoreIfMissing()
		.filename(ENV_FILE_NAME).load()
		.entries()
		.associate { it.key to it.value }
}

