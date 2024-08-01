package com.prueba.agendapro.validator.product

import com.prueba.agendapro.adapter.repository.ProductRepository
import com.prueba.agendapro.builder.ProductBuilder
import com.prueba.agendapro.exception.ErrorDomain
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.util.Optional
import kotlin.test.Test

@ExtendWith(MockKExtension::class)
class ValidationProductExistTest {

    @InjectMockKs
    private lateinit var validator: ValidationProductExist

    @MockK
    private lateinit var productRepository: ProductRepository

    @Test
    fun `validate fail`() {
        val product = ProductBuilder().build()
        every { productRepository.findProductBySku(product.sku) } returns Optional.of(product)

        assertThrows<ErrorDomain> {
            validator.validate(product)
        }
        verify(exactly = 1) { productRepository.findProductBySku(product.sku) }
    }

    @Test
    fun `validate not fail`() {
        val product = ProductBuilder().build()
        every { productRepository.findProductBySku(product.sku) } returns Optional.ofNullable(null)

        validator.validate(product)

        verify(exactly = 1) { productRepository.findProductBySku(product.sku) }
    }
}
