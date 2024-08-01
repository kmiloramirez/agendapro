package com.prueba.agendapro.adapter.repository

import com.prueba.agendapro.entity.Product
import com.prueba.agendapro.utilobjects.Page
import com.prueba.agendapro.utilobjects.Pageable
import java.util.Optional
import java.util.UUID

interface ProductRepository {
    fun findProductBySku(sku: String): Optional<Product>
    fun saveProduct(product: Product): Product
    fun findAllProductPageable(pageable: Pageable): Page<Product>
    fun deleteProduct(productId: UUID)
    fun findProductById(productId: UUID): Optional<Product>
}
