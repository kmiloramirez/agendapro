package com.prueba.agendapro.usecase.commandusecase.product

import com.prueba.agendapro.adapter.event.StaticsEventPublish
import com.prueba.agendapro.adapter.repository.ProductRepository
import com.prueba.agendapro.anotation.UseCase
import com.prueba.agendapro.entity.EventCategory
import com.prueba.agendapro.entity.Product
import com.prueba.agendapro.entity.enums.Action
import com.prueba.agendapro.entity.enums.EntityEvent
import com.prueba.agendapro.validator.Validation
import com.prueba.agendapro.validator.product.ValidationProductExist
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@UseCase
class CrateProductUseCase(
    private val validationProductExist: ValidationProductExist,
    private val validations: List<Validation<Product>> = listOf(validationProductExist),
    private val productRepository: ProductRepository,
    private val staticsEventPublish: StaticsEventPublish,
) {

    fun execute(product: Product): Product {
        validations.forEach { it.validate(product) }
        val productSaved = productRepository.saveProduct(product)
        publishCreateProduct(productSaved)
        return productSaved
    }

    private fun publishCreateProduct(product: Product) {
        val coroutineScope = CoroutineScope(Dispatchers.IO)
        coroutineScope.launch {
            val eventCategory = EventCategory(
                id = product.id,
                entityEvent = EntityEvent.produtc,
                action = Action.add_product
            )
            staticsEventPublish.publishEventCategory(eventCategory)
        }
    }
}
