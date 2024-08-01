package com.prueba.agendapro.adapter

import com.prueba.agendapro.adapter.statics.CategoryStatics
import com.prueba.agendapro.statics.CategoryStaticsVolatileMemory
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class CategoryStaticsAdapter(
    private val categoryStaticsVolatileMemory: CategoryStaticsVolatileMemory,
) : CategoryStatics {
    override fun addProductToCategory(category: String, productId: UUID) {
        categoryStaticsVolatileMemory.addProductCategoryStatics(category, productId)
    }

    override fun removeProductToCategory(category: String, productId: UUID) {
        categoryStaticsVolatileMemory.deleteProductCategoryStatics(category, productId)
    }

    override fun getProductsOfCategory(category: String): List<UUID> {
        return categoryStaticsVolatileMemory.findCategory(category)
    }
}
