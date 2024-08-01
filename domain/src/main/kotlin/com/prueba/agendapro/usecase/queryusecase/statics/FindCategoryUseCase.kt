package com.prueba.agendapro.usecase.queryusecase.statics

import com.prueba.agendapro.adapter.statics.CategoryStatics
import com.prueba.agendapro.anotation.UseCase
import com.prueba.agendapro.entity.CategoryInfo
import com.prueba.agendapro.usecase.queryusecase.product.FindProductsUseCase

@UseCase
class FindCategoryUseCase(
    private val repository: CategoryStatics,
    private val findProductsUseCase: FindProductsUseCase,
) {

    fun executeByCategory(category: String): CategoryInfo {
        val productFound = repository.getProductsOfCategory(category)
        return if (productFound.isNotEmpty()) {
            CategoryInfo(
                category = category,
                totalProducts = productFound.size,
                products = productFound.map { findProductsUseCase.executeById(it) }
            )
        } else {
            CategoryInfo(
                category = category,
                totalProducts = 0,
                products = emptyList()
            )
        }
    }
}


