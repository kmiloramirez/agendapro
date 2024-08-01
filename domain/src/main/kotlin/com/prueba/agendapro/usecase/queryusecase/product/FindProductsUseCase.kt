package com.prueba.agendapro.usecase.queryusecase.product

import com.prueba.agendapro.adapter.repository.ProductRepository
import com.prueba.agendapro.anotation.UseCase
import com.prueba.agendapro.entity.Product
import com.prueba.agendapro.exception.ErrorCode
import com.prueba.agendapro.exception.ErrorDomain
import com.prueba.agendapro.utilobjects.Page
import com.prueba.agendapro.utilobjects.Pageable
import java.util.UUID

@UseCase
class FindProductsUseCase(
    private val repository: ProductRepository,
) {

    fun executeById(productId: UUID): Product {
        val productFound = repository.findProductById(productId)
        return if (productFound.isPresent) {
            productFound.get()
        } else {
            throw ErrorDomain(ErrorCode.product_not_found, ErrorCode.product_not_found.messageKey)
        }
    }

    fun executeForPage(pageable: Pageable): Page<Product> {
        val pageResult = repository.findAllProductPageable(pageable)
        return pageResult
    }
}
