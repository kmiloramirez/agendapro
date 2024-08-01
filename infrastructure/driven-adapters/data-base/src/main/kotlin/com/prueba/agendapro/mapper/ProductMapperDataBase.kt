package com.prueba.agendapro.mapper

import com.prueba.agendapro.entity.Product
import com.prueba.agendapro.model.ProductModel
import org.springframework.stereotype.Component
import com.prueba.agendapro.utilobjects.Page as PageDomain
import org.springframework.data.domain.Page as PageSpring

@Component
class ProductMapperDataBase {

    fun entityToModel(productEntity: Product): ProductModel {
        return ProductModel().apply {
            id = productEntity.id
            sku = productEntity.sku
            name = productEntity.name
            price = productEntity.price
            category = productEntity.category
        }
    }

    fun modelToEntity(productModel: ProductModel): Product {
        return Product(
            id = productModel.id,
            sku = productModel.sku,
            name = productModel.name,
            price = productModel.price,
            category = productModel.category,
        )
    }

    fun modelToEntityPage(resultPage: PageSpring<ProductModel>): PageDomain<Product> {
        return PageDomain(
            totalElements = resultPage.totalElements.toInt(),
            totalPages = resultPage.totalPages,
            currentPage = resultPage.number,
            content = resultPage.content.map { modelToEntity(it) }
        )
    }
}
