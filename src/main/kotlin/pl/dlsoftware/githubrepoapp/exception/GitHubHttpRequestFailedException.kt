package pl.dlsoftware.githubrepoapp.exception

class GitHubHttpRequestFailedException(statusCode: Int, message: String) : RuntimeException(message)