package pl.dlsoftware.app.exception

class GitHubHttpRequestFailedException(val statusCode: Int, val errorMessage: String) : RuntimeException(errorMessage)