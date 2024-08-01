package com.prueba.agendapro.usecase.queryusecase.statics

import com.prueba.agendapro.adapter.statics.CategoryStatics
import com.prueba.agendapro.builder.ProductBuilder
import com.prueba.agendapro.usecase.queryusecase.product.FindProductsUseCase
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.extension.ExtendWith
import java.util.UUID

@ExtendWith(MockKExtension::class)
class FindCategoryUseCaseTest {

    @InjectMockKs
    private lateinit var useCase: FindCategoryUseCase

    @MockK
    private lateinit var repository: CategoryStatics

    @MockK
    private lateinit var findProductsUseCase: FindProductsUseCase

    @Test
    fun `execute by category when category not has products`() {
        val categoryTest = "Category test"
        val productsFound = emptyList<UUID>()
        every { repository.getProductsOfCategory(categoryTest) } returns productsFound

        val result = useCase.executeByCategory(categoryTest)

        assertTrue(result.products.isEmpty())
    }

    @Test
    fun `execute by category when category has products`() {
        val categoryTest = "Category test"
        val productUUID = UUID.randomUUID()
        val productsFound = listOf(productUUID)
        val productFound = ProductBuilder().id(productUUID).category(categoryTest).build()
        every { repository.getProductsOfCategory(categoryTest) } returns productsFound
        every { findProductsUseCase.executeById(productUUID) } returns productFound

        val result = useCase.executeByCategory(categoryTest)

        assertTrue(result.products.isNotEmpty())
        assertEquals(productsFound.size, result.totalProducts)
    }
}
