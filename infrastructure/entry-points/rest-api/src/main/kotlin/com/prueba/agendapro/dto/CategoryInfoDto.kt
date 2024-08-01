package com.prueba.agendapro.dto

data class CategoryInfoDto(
    val category: String,
    val totalProducts: Int,
    val products: List<ProductDto>,
)
