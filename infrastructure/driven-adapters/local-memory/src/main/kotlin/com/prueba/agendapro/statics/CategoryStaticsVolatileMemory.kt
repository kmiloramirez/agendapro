package com.prueba.agendapro.statics

import org.springframework.stereotype.Component
import java.util.UUID

@Component
class CategoryStaticsVolatileMemory {

    private val categoryProductsStatics: MutableMap<String, MutableSet<UUID>> = mutableMapOf()

    fun addProductCategoryStatics(category: String, productId: UUID) {
        if (categoryProductsStatics.containsKey(category)) {
            categoryProductsStatics[category]?.add(productId)
        } else {
            categoryProductsStatics[category] = mutableSetOf(productId)
        }
    }

    fun deleteProductCategoryStatics(category: String, productId: UUID) {
        categoryProductsStatics[category]?.remove(productId)
    }

    fun findCategory(category: String): List<UUID> {
        return categoryProductsStatics[category]?.toList() ?: emptyList()
    }
}
