package com.prueba.agendapro.validator.product

import com.prueba.agendapro.adapter.repository.ProductRepository
import com.prueba.agendapro.anotation.ValidationDomain
import com.prueba.agendapro.entity.Product
import com.prueba.agendapro.exception.ErrorCode
import com.prueba.agendapro.exception.ErrorDomain
import com.prueba.agendapro.validator.Validation

@ValidationDomain
class ValidationProductExist(private val productRepository: ProductRepository) :
    Validation<Product> {

    override fun validate(entity: Product) {
        if (productRepository.findProductBySku(entity.sku).isPresent) {
            throw ErrorDomain(ErrorCode.product_exist, ErrorCode.product_exist.messageKey)
        }
    }
}
