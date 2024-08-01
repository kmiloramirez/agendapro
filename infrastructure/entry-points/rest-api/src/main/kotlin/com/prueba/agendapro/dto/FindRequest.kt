package com.prueba.agendapro.dto

data class FindRequest(
    val search: String? = null,
    val page: Int = 0,
    val size: Int = 10,
)
