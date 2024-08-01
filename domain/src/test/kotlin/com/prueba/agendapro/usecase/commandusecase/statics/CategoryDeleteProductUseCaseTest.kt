package com.prueba.agendapro.usecase.commandusecase.statics

import com.prueba.agendapro.adapter.statics.CategoryStatics
import com.prueba.agendapro.builder.EventCategoryBuilder
import com.prueba.agendapro.entity.enums.Action
import com.prueba.agendapro.entity.enums.EntityEvent
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
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.util.UUID

@ExtendWith(MockKExtension::class)
class CategoryDeleteProductUseCaseTest {

    @InjectMockKs
    private lateinit var useCase: CategoryDeleteProductUseCase

    @MockK
    private lateinit var categoryStatics: CategoryStatics

    @Test
    fun execute() {
        val productId = UUID.randomUUID()
        val categoryTest = "Category test"
        val slotCategory = slot<String>()
        val slotProductId = slot<UUID>()
        val event = EventCategoryBuilder().id(productId).entityEvent(EntityEvent.produtc).action(
            Action.delete_product
        ).oldCategory(categoryTest).build()
        every {
            categoryStatics.removeProductToCategory(
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

        verify(exactly = 1) { categoryStatics.removeProductToCategory(any(), any()) }
    }

    @Test
    fun `execute when old category is null`() {
        val productId = UUID.randomUUID()
        val slotCategory = slot<String>()
        val slotProductId = slot<UUID>()
        val event = EventCategoryBuilder().id(productId).entityEvent(EntityEvent.produtc).action(
            Action.delete_product
        ).oldCategory(null).build()
        every {
            categoryStatics.removeProductToCategory(
                capture(slotCategory),
                capture(slotProductId)
            )
        } just Runs

        assertThrows<IllegalArgumentException> {
            useCase.execute(event)
        }
    }

    @Test
    fun getTypeAction(){

        assertEquals(Action.delete_product, useCase.getTypeAction())
    }
}
