package pl.dlsoftware.app.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "github.api")
data class GitHubConfigurationProperties(
    val host: String,
    val version: String,
    val token: String
)