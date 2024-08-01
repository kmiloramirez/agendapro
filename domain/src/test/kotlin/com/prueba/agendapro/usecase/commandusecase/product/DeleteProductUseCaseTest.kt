package com.prueba.agendapro.usecase.commandusecase.product

import com.prueba.agendapro.adapter.event.StaticsEventPublish
import com.prueba.agendapro.adapter.repository.ProductRepository
import com.prueba.agendapro.builder.ProductBuilder
import com.prueba.agendapro.entity.EventCategory
import com.prueba.agendapro.exception.ErrorCode
import com.prueba.agendapro.exception.ErrorDomain
import com.prueba.agendapro.usecase.queryusecase.product.FindProductsUseCase
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

import org.junit.jupiter.api.extension.ExtendWith
import java.util.UUID
import kotlin.test.assertTrue

@ExtendWith(MockKExtension::class)
class DeleteProductUseCaseTest {

    @InjectMockKs
    private lateinit var useCase: DeleteProductUseCase

    @MockK
    private lateinit var repository: ProductRepository

    @MockK
    private lateinit var findProductsUseCase: FindProductsUseCase

    @MockK
    private lateinit var staticsEventPublish: StaticsEventPublish

    @Test
    fun `execute`() {
        val productId = UUID.randomUUID()
        val categoryTest = "Category"
        val product = ProductBuilder().id(productId).category(categoryTest).build()
        every { findProductsUseCase.executeById(productId) } returns product
        every { repository.deleteProduct(productId) } just Runs
        val slotEvent = slot<EventCategory>()
        every {
            staticsEventPublish.publishEventCategory(capture(slotEvent))
        } just runs

        runBlocking {
            useCase.execute(productId)
            delay(500)
        }

        assertTrue(slotEvent.isCaptured)
        verify(exactly = 1) { findProductsUseCase.executeById(productId) }
        verify(exactly = 1) { repository.deleteProduct(productId) }
        verify(exactly = 1) { staticsEventPublish.publishEventCategory(any()) }
    }

    @Test
    fun `execute when product doesn't exist`() {
        val productId = UUID.randomUUID()
        every { repository.deleteProduct(productId) } just Runs
        every { findProductsUseCase.executeById(productId) } throws ErrorDomain(
            ErrorCode.product_not_found,
            ErrorCode.product_not_found.messageKey
        )

        useCase.execute(productId)

        verify(exactly = 1) { findProductsUseCase.executeById(productId) }
        verify(exactly = 0) { repository.deleteProduct(productId) }
        verify(exactly = 0) { staticsEventPublish.publishEventCategory(any()) }
    }

    @Test
    fun `execute whit others errors`() {
        val productId = UUID.randomUUID()
        val categoryTest = "Category"
        val product = ProductBuilder().id(productId).category(categoryTest).build()
        every { findProductsUseCase.executeById(productId) } returns product
        every { repository.deleteProduct(productId) } throws Exception()

        assertThrows<ErrorDomain> {
            useCase.execute(productId)
        }


        verify(exactly = 1) { findProductsUseCase.executeById(productId) }
        verify(exactly = 1) { repository.deleteProduct(productId) }
        verify(exactly = 0) { staticsEventPublish.publishEventCategory(any()) }
    }
}
