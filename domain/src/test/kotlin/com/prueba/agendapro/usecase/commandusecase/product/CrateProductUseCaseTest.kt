package com.prueba.agendapro.usecase.commandusecase.product

import com.prueba.agendapro.adapter.event.StaticsEventPublish
import com.prueba.agendapro.adapter.repository.ProductRepository
import com.prueba.agendapro.builder.ProductBuilder
import com.prueba.agendapro.entity.EventCategory
import com.prueba.agendapro.entity.Product
import com.prueba.agendapro.entity.enums.Action
import com.prueba.agendapro.usecase.commandusecase.product.CrateProductUseCase
import com.prueba.agendapro.validator.product.ValidationProductExist
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
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExtendWith(MockKExtension::class)
class CrateProductUseCaseTest {

    @InjectMockKs
    lateinit var crateProductUseCase: CrateProductUseCase

    @MockK
    lateinit var validationProductExist: ValidationProductExist

    @MockK
    lateinit var productRepository: ProductRepository

    @MockK
    lateinit var staticsEventPublish: StaticsEventPublish

    @Test
    fun execute() {
        val product = ProductBuilder().build()
        every { validationProductExist.validate(product) } just runs
        val slotProduct = slot<Product>()
        every { productRepository.saveProduct(capture(slotProduct)) } returns product
        val slotEvent = slot<EventCategory>()
        every {
            staticsEventPublish.publishEventCategory(capture(slotEvent))
        } just runs
        val result: Product
        runBlocking {
            result = crateProductUseCase.execute(product)
            delay(500)
        }
        assertTrue(slotProduct.isCaptured)
        val productSendToSaved = slotProduct.captured

        verify(exactly = 1) { validationProductExist.validate(product) }
        verify(exactly = 1) { productRepository.saveProduct(any()) }
        verify(exactly = 1) {
            staticsEventPublish.publishEventCategory(any())
        }
        assertEquals(productSendToSaved, result)
        assertTrue(slotEvent.isCaptured)
        val eventCaptured = slotEvent.captured
        assertEquals(Action.add_product, eventCaptured.action)
    }
}
