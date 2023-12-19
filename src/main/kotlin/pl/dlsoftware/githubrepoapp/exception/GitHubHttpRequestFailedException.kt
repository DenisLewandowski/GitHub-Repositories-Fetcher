package pl.dlsoftware.githubrepoapp.exception

class GitHubHttpRequestFailedException(val statusCode: Int, val errorMessage: String) : RuntimeException(errorMessage)