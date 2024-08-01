package com.prueba.agendapro.queries

import com.prueba.agendapro.dto.FindRequest
import com.prueba.agendapro.dto.ProductDto
import com.prueba.agendapro.mapper.ProductMapperApi
import com.prueba.agendapro.usecase.queryusecase.product.FindProductsUseCase
import com.prueba.agendapro.utilobjects.Page
import com.prueba.agendapro.utilobjects.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController("productQueriesV1")
@RequestMapping(value = ["v1/products"])
class ProductQueries(
    private val productMapperApi: ProductMapperApi,
    private val findProductsUseCase: FindProductsUseCase,
) {

    @GetMapping
    fun getPaymentMethodLinks(requestDTO: FindRequest): Page<ProductDto> {
        val page = findProductsUseCase.executeForPage(
            Pageable(
                page = requestDTO.page,
                size = requestDTO.size,
                search = requestDTO.search
            )
        )
        return productMapperApi.modelToDtoPage(page)
    }

    @GetMapping("{productId}")
    fun findProductById(@PathVariable productId: UUID): ProductDto {
        return productMapperApi.entityToDto(findProductsUseCase.executeById(productId))
    }
}


