package com.prueba.agendapro.validator

fun interface Validation<T> {
    fun validate(entity: T)
}
