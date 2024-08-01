package com.prueba.agendapro.entity

import java.util.UUID

class Product(
    val id: UUID = UUID.randomUUID(),
    val sku: String,
    val name: String,
    val price: Double,
    val category: String,
)
