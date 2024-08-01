package com.prueba.agendapro.usecase.commandusecase.product

import com.prueba.agendapro.adapter.event.StaticsEventPublish
import com.prueba.agendapro.adapter.repository.ProductRepository
import com.prueba.agendapro.builder.ProductBuilder
import com.prueba.agendapro.builder.ProductUpdateBuilder
import com.prueba.agendapro.entity.EventCategory
import com.prueba.agendapro.entity.Product
import com.prueba.agendapro.entity.ProductUpdate
import com.prueba.agendapro.entity.enums.Action
import com.prueba.agendapro.entity.enums.EntityEvent
import com.prueba.agendapro.usecase.queryusecase.product.FindProductsUseCase
import com.prueba.agendapro.validator.product.ValidationProductExist
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.extension.ExtendWith
import java.util.UUID

@ExtendWith(MockKExtension::class)
class UpdateProductUseCaseTest {

    @InjectMockKs
    lateinit var useCase: UpdateProductUseCase

    @MockK
    lateinit var findProductUseCase: FindProductsUseCase

    @MockK
    lateinit var productRepository: ProductRepository

    @MockK
    lateinit var staticsEventPublish: StaticsEventPublish

    @Test
    fun `execute when all attribute to update is null `() {
        val productId = UUID.randomUUID()
        val sku = "SKU-0001"
        var name = "Test product name"
        var price = 10000.0
        var category = "Test category"
        val product =
            ProductBuilder().id(productId).sku(sku).name(name).price(price).category(category)
                .build()
        val productUpdate =
            ProductUpdateBuilder().sku(null).name(null).price(null).category(null).build()
        every { findProductUseCase.executeById(productId) } returns product
        val slotProduct = slot<Product>()
        every { productRepository.saveProduct(capture(slotProduct)) } returns product

        useCase.execute(productId, productUpdate)

        assertTrue(slotProduct.isCaptured)
        val productCaptured = slotProduct.captured
        assertEquals(productId, productCaptured.id)
        assertEquals(sku, productCaptured.sku)
        assertEquals(name, productCaptured.name)
        assertEquals(price, productCaptured.price)
        assertEquals(category, productCaptured.category)

        verify(exactly = 1) { findProductUseCase.executeById(productId) }
        verify(exactly = 1) { productRepository.saveProduct(any()) }
        verify(exactly = 0) { staticsEventPublish.publishEventCategory(any()) }
    }

    @Test
    fun `execute when all attribute to update is empty `() {
        val productId = UUID.randomUUID()
        val sku = "SKU-0001"
        var name = "Test product name"
        var price = 10000.0
        var category = "Test category"
        val product =
            ProductBuilder().id(productId).sku(sku).name(name).price(price).category(category)
                .build()
        val productUpdate = ProductUpdateBuilder().sku("").name("").price(null).category("").build()
        every { findProductUseCase.executeById(productId) } returns product
        val slotProduct = slot<Product>()
        every { productRepository.saveProduct(capture(slotProduct)) } returns product

        useCase.execute(productId, productUpdate)

        assertTrue(slotProduct.isCaptured)
        val productCaptured = slotProduct.captured
        assertEquals(productId, productCaptured.id)
        assertEquals(sku, productCaptured.sku)
        assertEquals(name, productCaptured.name)
        assertEquals(price, productCaptured.price)
        assertEquals(category, productCaptured.category)

        verify(exactly = 1) { findProductUseCase.executeById(productId) }
        verify(exactly = 1) { productRepository.saveProduct(any()) }
        verify(exactly = 0) { staticsEventPublish.publishEventCategory(any()) }
    }

    @Test
    fun `execute when all attribute change`() {
        val productId = UUID.randomUUID()
        val sku = "SKU-0001"
        var name = "Test product name"
        var price = 10000.0
        var category = "Test category"
        val product =
            ProductBuilder().id(productId).sku(sku).name(name).price(price).category(category)
                .build()
        val skuNew = "SKU-0002"
        var nameNew = "Test product name 2"
        var priceNew = 20000.0
        var categoryNew = "Test category 2"
        val productUpdate =
            ProductUpdateBuilder().sku(skuNew).name(nameNew).price(priceNew).category(categoryNew)
                .build()
        every { findProductUseCase.executeById(productId) } returns product
        val slotProduct = slot<Product>()
        every { productRepository.saveProduct(capture(slotProduct)) } returns product
        val slotEvent = slot<EventCategory>()
        every {
            staticsEventPublish.publishEventCategory(capture(slotEvent))
        } just runs

        runBlocking {
            useCase.execute(productId, productUpdate)
            delay(500)
        }


        assertTrue(slotProduct.isCaptured)
        assertTrue(slotEvent.isCaptured)
        val productCaptured = slotProduct.captured
        assertEquals(productId, productCaptured.id)
        assertEquals(skuNew, productCaptured.sku)
        assertEquals(nameNew, productCaptured.name)
        assertEquals(priceNew, productCaptured.price)
        assertEquals(categoryNew, productCaptured.category)

        verify(exactly = 1) { findProductUseCase.executeById(productId) }
        verify(exactly = 1) { productRepository.saveProduct(any()) }
        verify(exactly = 2) { staticsEventPublish.publishEventCategory(any()) }
    }
}

/*fun execute(productId: UUID, productToUpdate: ProductUpdate): Product {
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
*/
