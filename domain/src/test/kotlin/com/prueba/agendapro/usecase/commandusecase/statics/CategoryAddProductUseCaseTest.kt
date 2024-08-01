package com.prueba.agendapro.usecase.commandusecase.statics

import com.prueba.agendapro.adapter.statics.CategoryStatics
import com.prueba.agendapro.builder.EventCategoryBuilder
import com.prueba.agendapro.builder.ProductBuilder
import com.prueba.agendapro.entity.enums.Action
import com.prueba.agendapro.entity.enums.EntityEvent
import com.prueba.agendapro.usecase.queryusecase.product.FindProductsUseCase
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.UUID

@ExtendWith(MockKExtension::class)
class CategoryAddProductUseCaseTest {

    @InjectMockKs
    private lateinit var useCase: CategoryAddProductUseCase

    @MockK
    private lateinit var findProductsUseCase: FindProductsUseCase

    @MockK
    private lateinit var categoryStatics: CategoryStatics

    @Test
    fun execute() {
        val productId = UUID.randomUUID()
        val categoryTest = "Category test"
        val product = ProductBuilder().id(productId).category(categoryTest).build()
        val slotCategory = slot<String>()
        val slotProductId = slot<UUID>()
        val event = EventCategoryBuilder().id(productId).entityEvent(EntityEvent.produtc).action(
            Action.add_product
        ).build()
        every { findProductsUseCase.executeById(productId) } returns product
        every {
            categoryStatics.addProductToCategory(
                capture(slotCategory),
                capture(slotProductId)
            )
        } just Runs


        useCase.execute(event)

        assertTrue(slotCategory.captured == categoryTest)
        assertTrue(slotProductId.captured == productId)
        val categoryCaptured = slotCategory.captured
        val productCaptured = slotProductId.captured
        assertEquals(categoryTest, categoryCaptured)
        assertEquals(productId, productCaptured)

        verify(exactly = 1) { findProductsUseCase.executeById(productId) }
        verify(exactly = 1) { categoryStatics.addProductToCategory(any(), any()) }
    }

    @Test
    fun getTypeAction(){

        assertEquals(Action.add_product, useCase.getTypeAction())
    }
}
