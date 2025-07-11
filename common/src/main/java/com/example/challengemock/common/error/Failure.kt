package com.example.challengemock.common.error

import java.io.IOException

sealed class Failure {
    data class NetworkError(val exception: IOException) : Failure()
    data class ServerError(val code: Int, val message: String) : Failure()
    data class UnknownError(val throwable: Throwable) : Failure()
}
