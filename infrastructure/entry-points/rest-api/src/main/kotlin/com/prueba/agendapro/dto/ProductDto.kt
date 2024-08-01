package com.prueba.agendapro.dto

import java.util.UUID

data class ProductDto(
    val id: UUID? = null,
    val sku: String? = null,
    val name: String? = null,
    val price: Double = 0.0,
    val category: String? = null,
)
