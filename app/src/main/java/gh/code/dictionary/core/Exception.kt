package gh.code.dictionary.core

open class AppException(
    message: String? = "",
    cause: Throwable? = null
) : Exception(message, cause)

class ConnectionException(
    cause: Exception
) : AppException(cause = cause)

class DataNotFoundException(
    val code: Int,
    message: String?
) : AppException(message = message)

class ParseBackendException(
    cause: Throwable
) : AppException(cause = cause)
