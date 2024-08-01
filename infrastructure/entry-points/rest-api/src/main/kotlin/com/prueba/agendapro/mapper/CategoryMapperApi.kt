package com.prueba.agendapro.mapper

import com.prueba.agendapro.dto.CategoryInfoDto
import com.prueba.agendapro.entity.CategoryInfo
import org.springframework.stereotype.Component

@Component
class CategoryMapperApi(
    private val productMapperApi: ProductMapperApi,
) {
    fun entityToDto(categoryInfo: CategoryInfo): CategoryInfoDto {
        return CategoryInfoDto(
            category = categoryInfo.category,
            totalProducts = categoryInfo.totalProducts,
            products = categoryInfo.products.map { productMapperApi.entityToDto(it) }
        )
    }
}
