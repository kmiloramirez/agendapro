package com.prueba.agendapro.dto

data class ProductCreateRequest(
    val sku: String,
    val name: String,
    val price: Double,
    val category: String,
)
