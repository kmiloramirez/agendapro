package com.prueba.agendapro.usecase.queryusecase.product

import com.prueba.agendapro.adapter.repository.ProductRepository
import com.prueba.agendapro.builder.PageBuilder
import com.prueba.agendapro.builder.PageableBuilder
import com.prueba.agendapro.builder.ProductBuilder
import com.prueba.agendapro.entity.Product
import com.prueba.agendapro.exception.ErrorDomain
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

import org.junit.jupiter.api.extension.ExtendWith
import java.util.Optional
import java.util.UUID

@ExtendWith(MockKExtension::class)
class FindProductsUseCaseTest {

    @InjectMockKs
    private lateinit var useCase: FindProductsUseCase

    @MockK
    private lateinit var repository: ProductRepository

    @Test
    fun `execute by id when product id doesn't exist `() {
        val productId = UUID.randomUUID()
        every { repository.findProductById(productId) } returns Optional.ofNullable(null)

        assertThrows<ErrorDomain> {
            useCase.executeById(productId)
        }
        verify(exactly = 1) { repository.findProductById(productId) }
    }

    @Test
    fun `execute by id when product id exist `() {
        val productId = UUID.randomUUID()
        val product = ProductBuilder().id(productId).build()

        every { repository.findProductById(productId) } returns Optional.ofNullable(product)
        val result = useCase.executeById(productId)

        assertEquals(product, result)
        verify(exactly = 1) { repository.findProductById(productId) }
    }

    @Test
    fun executeForPage() {
        val pageable = PageableBuilder().build()
        val product = ProductBuilder().build()
        val listOfProducts = listOf(product)
        val page = PageBuilder<Product>()
            .content(listOfProducts)
            .totalPages(1)
            .totalElements(listOfProducts.size)
            .currentPage(0)
            .build()
        every { repository.findAllProductPageable(pageable) } returns page
        val result = useCase.executeForPage(pageable)
        assertEquals(listOfProducts, result.content)
        verify(exactly = 1) { repository.findAllProductPageable(pageable) }
    }
}
