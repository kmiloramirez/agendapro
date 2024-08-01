package com.prueba.agendapro.adapter

import com.prueba.agendapro.adapter.repository.ProductRepository
import com.prueba.agendapro.entity.Product
import com.prueba.agendapro.mapper.ProductMapperDataBase
import com.prueba.agendapro.model.ProductModel
import com.prueba.agendapro.repository.ProductRepositoryJpa
import com.prueba.agendapro.utilobjects.Page
import com.prueba.agendapro.utilobjects.Pageable
import io.github.perplexhub.rsql.RSQLJPASupport
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component
import java.util.Optional
import java.util.UUID

@Component
class ProductRepositoryAdapter(
    private val productRepositoryJpa: ProductRepositoryJpa,
    private val productMapperDataBase: ProductMapperDataBase,
) : ProductRepository {
    override fun findProductBySku(sku: String): Optional<Product> {
        return productRepositoryJpa.findBySku(sku).map { productMapperDataBase.modelToEntity(it) }
    }

    override fun saveProduct(product: Product): Product {
        return productMapperDataBase.modelToEntity(
            productRepositoryJpa.save(
                productMapperDataBase.entityToModel(
                    product
                )
            )
        )
    }

    override fun findAllProductPageable(pageable: Pageable): Page<Product> {
        val specification = createSpecificationWhere(pageable)
        val pageRequest = PageRequest.of(pageable.page, pageable.size)
        return findBySpecs(specification, pageRequest)
    }

    override fun deleteProduct(productId: UUID) {
        productRepositoryJpa.deleteById(productId)
    }

    override fun findProductById(productId: UUID): Optional<Product> {
        return productRepositoryJpa.findById(productId)
            .map { productMapperDataBase.modelToEntity(it) }
    }

    private fun createSpecificationWhere(pageable: Pageable): Specification<ProductModel> {
        val baseSpecificationWhere =
            RSQLJPASupport.toSpecification<ProductModel?>(pageable.search)
        return baseSpecificationWhere
    }

    private fun findBySpecs(
        spec: Specification<ProductModel>,
        pageRequest: PageRequest,
    ): Page<Product> {
        val result = productRepositoryJpa.findAll(spec, pageRequest)
        return productMapperDataBase.modelToEntityPage(result)
    }
}
