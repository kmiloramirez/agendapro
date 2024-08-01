package com.prueba.agendapro.usecase.commandusecase.statics

import com.prueba.agendapro.adapter.statics.CategoryStatics
import com.prueba.agendapro.anotation.UseCase
import com.prueba.agendapro.entity.EventCategory
import com.prueba.agendapro.entity.enums.Action
import com.prueba.agendapro.usecase.queryusecase.product.FindProductsUseCase

@UseCase
class CategoryAddProductUseCase(
    private val findProductsUseCase: FindProductsUseCase,
    private val categoryStatics: CategoryStatics,
) : StrategyCategoryProduct {

    override fun getTypeAction() = Action.add_product

    override fun execute(eventCategory: EventCategory) {
        val productId = eventCategory.id
        val product = findProductsUseCase.executeById(productId)
        val category = product.category
        categoryStatics.addProductToCategory(category, productId)
    }
}
