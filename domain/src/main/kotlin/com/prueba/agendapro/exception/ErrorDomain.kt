package com.prueba.agendapro.exception

open class ErrorDomain(
    val error: ErrorCode,
    message: String,
    val typeError: String = ErrorDomain::class.java.simpleName,
) : RuntimeException(message) {
}
