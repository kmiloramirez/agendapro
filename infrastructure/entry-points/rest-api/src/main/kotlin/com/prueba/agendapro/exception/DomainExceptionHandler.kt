package com.prueba.agendapro.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class DomainExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(ErrorDomain::class)
    fun handleMiExcepcion(error: ErrorDomain): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(error.error.code)
            .body(
                ErrorResponse(
                    error.error.code, error.error.messageKey, error.typeError
                )
            )
    }
}
