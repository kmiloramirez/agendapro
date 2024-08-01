package com.prueba.agendapro.utilobjects

data class Page<T>(
    val content: List<T>,
    val totalPages: Int,
    val totalElements: Int,
    val currentPage: Int,
)
