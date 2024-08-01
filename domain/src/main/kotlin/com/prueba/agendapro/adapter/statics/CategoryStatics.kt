package com.prueba.agendapro.adapter.statics

import java.util.UUID

interface CategoryStatics {

    fun addProductToCategory(category: String, productId: UUID)
    fun removeProductToCategory(category: String, productId: UUID)
    fun getProductsOfCategory(category: String): List<UUID>
}
