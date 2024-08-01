package com.prueba.agendapro.usecase.commandusecase.product

import com.prueba.agendapro.adapter.event.StaticsEventPublish
import com.prueba.agendapro.adapter.repository.ProductRepository
import com.prueba.agendapro.anotation.UseCase
import com.prueba.agendapro.entity.EventCategory
import com.prueba.agendapro.entity.enums.Action
import com.prueba.agendapro.entity.enums.EntityEvent
import com.prueba.agendapro.exception.ErrorCode
import com.prueba.agendapro.exception.ErrorDomain
import com.prueba.agendapro.usecase.queryusecase.product.FindProductsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

@UseCase
class DeleteProductUseCase(
    private val productRepository: ProductRepository,
    private val findProductsUseCase: FindProductsUseCase,
    private val staticsEventPublish: StaticsEventPublish,
) {

    fun execute(productId: UUID) {
        runCatching {
            val categoryProduct = findProductsUseCase.executeById(productId).category
            productRepository.deleteProduct(productId)
            updateCategoryStatics(productId, categoryProduct)
        }.onFailure { exception ->
            when (exception) {
                is ErrorDomain -> {
                    Any()
                }

                else -> throw ErrorDomain(
                    ErrorCode.error_delete_product,
                    ErrorCode.error_delete_product.messageKey
                )
            }
        }
    }

    private fun updateCategoryStatics(productId: UUID, category: String) {
        val coroutineScope = CoroutineScope(Dispatchers.IO)
        coroutineScope.launch {
            val eventCategory = EventCategory(
                id = productId,
                entityEvent = EntityEvent.produtc,
                action = Action.delete_product,
                oldCategory = category,
            )
            staticsEventPublish.publishEventCategory(eventCategory)
        }
    }
}
