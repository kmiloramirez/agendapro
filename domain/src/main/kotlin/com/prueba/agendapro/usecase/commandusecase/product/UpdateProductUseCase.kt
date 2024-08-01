package com.prueba.agendapro.usecase.commandusecase.product

import com.prueba.agendapro.adapter.event.StaticsEventPublish
import com.prueba.agendapro.adapter.repository.ProductRepository
import com.prueba.agendapro.anotation.UseCase
import com.prueba.agendapro.entity.EventCategory
import com.prueba.agendapro.entity.Product
import com.prueba.agendapro.entity.ProductUpdate
import com.prueba.agendapro.entity.enums.Action
import com.prueba.agendapro.entity.enums.EntityEvent
import com.prueba.agendapro.usecase.queryusecase.product.FindProductsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

@UseCase
class UpdateProductUseCase(
    private val findProductUseCase: FindProductsUseCase,
    private val productRepository: ProductRepository,
    private val staticsEventPublish: StaticsEventPublish,
) {
    fun execute(productId: UUID, productToUpdate: ProductUpdate): Product {
        val actualProduct = findProductUseCase.executeById(productId)
        val newSku =
            if (!productToUpdate.sku.isNullOrBlank()) productToUpdate.sku else actualProduct.sku
        val newName =
            if (!productToUpdate.name.isNullOrBlank()) productToUpdate.name else actualProduct.name
        val newPrice = productToUpdate.price ?: actualProduct.price
        var chageCategory = false
        val newCategory = if (!productToUpdate.category.isNullOrBlank()) {
            chageCategory = true
            productToUpdate.category
        } else actualProduct.category
        val productUpdate = Product(
            id = actualProduct.id,
            sku = newSku,
            name = newName,
            price = newPrice,
            category = newCategory
        )
        val product = productRepository.saveProduct(productUpdate)
        if (chageCategory) {
            updateCategoryStatics(productId, actualProduct.category)
        }
        return product
    }

    private fun updateCategoryStatics(productId: UUID, oldCategory: String) {
        val coroutineScope = CoroutineScope(Dispatchers.IO)
        coroutineScope.launch {
            coroutineScope.launch {
                val eventCategory = EventCategory(
                    id = productId,
                    entityEvent = EntityEvent.produtc,
                    action = Action.delete_product,
                    oldCategory = oldCategory,
                )
                staticsEventPublish.publishEventCategory(eventCategory)
            }
            coroutineScope.launch {
                val eventCategory = EventCategory(
                    id = productId,
                    entityEvent = EntityEvent.produtc,
                    action = Action.add_product
                )
                staticsEventPublish.publishEventCategory(eventCategory)
            }
        }
    }
}
