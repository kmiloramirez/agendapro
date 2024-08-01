package com.prueba.agendapro.repository

import com.prueba.agendapro.model.ProductModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface ProductRepositoryJpa : JpaRepository<ProductModel, UUID>,
    JpaSpecificationExecutor<ProductModel> {
    fun findBySku(sku: String): Optional<ProductModel>
}
