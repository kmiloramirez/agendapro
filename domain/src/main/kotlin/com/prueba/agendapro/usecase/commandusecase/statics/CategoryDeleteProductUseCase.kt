package com.prueba.agendapro.usecase.commandusecase.statics

import com.prueba.agendapro.adapter.statics.CategoryStatics
import com.prueba.agendapro.anotation.UseCase
import com.prueba.agendapro.entity.EventCategory
import com.prueba.agendapro.entity.enums.Action

@UseCase
class CategoryDeleteProductUseCase(
    private val categoryStatics: CategoryStatics,
) : StrategyCategoryProduct {
    override fun getTypeAction() = Action.delete_product

    override fun execute(eventCategory: EventCategory) {
        val productId = eventCategory.id
        val category = requireNotNull(eventCategory.oldCategory)
        categoryStatics.removeProductToCategory(category, productId)
    }
}
