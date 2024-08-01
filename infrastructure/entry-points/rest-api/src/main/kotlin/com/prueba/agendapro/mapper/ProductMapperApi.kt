package com.prueba.agendapro.mapper

import com.prueba.agendapro.dto.ProductCreateRequest
import com.prueba.agendapro.dto.ProductDto
import com.prueba.agendapro.dto.ProductUpdateRequest
import com.prueba.agendapro.entity.Product
import com.prueba.agendapro.entity.ProductUpdate
import com.prueba.agendapro.utilobjects.Page
import org.springframework.stereotype.Component

@Component
class ProductMapperApi {

    fun createProductToEntity(createRequest: ProductCreateRequest): Product {
        return Product(
            sku = createRequest.sku,
            name = createRequest.name,
            price = createRequest.price,
            category = createRequest.category,
        )
    }

    fun updateProductToUpdateEntity(productUpdateRequest: ProductUpdateRequest): ProductUpdate {
        return ProductUpdate(
            sku = productUpdateRequest.sku,
            name = productUpdateRequest.name,
            price = productUpdateRequest.price,
            category = productUpdateRequest.category,
        )
    }

    fun entityToDto(product: Product): ProductDto {
        return ProductDto(
            id = product.id,
            sku = product.sku,
            name = product.name,
            price = product.price,
            category = product.category,
        )
    }

    fun modelToDtoPage(resultPage: Page<Product>): Page<ProductDto> {
        return Page(
            totalElements = resultPage.totalElements,
            totalPages = resultPage.totalPages,
            currentPage = resultPage.currentPage,
            content = resultPage.content.map { entityToDto(it) }
        )
    }
}
