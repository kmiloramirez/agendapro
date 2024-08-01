package com.prueba.agendapro.queries

import com.prueba.agendapro.dto.CategoryInfoDto
import com.prueba.agendapro.mapper.CategoryMapperApi
import com.prueba.agendapro.usecase.queryusecase.statics.FindCategoryUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController("categoryQueriesV1")
@RequestMapping(value = ["v1/category"])
class CategoryQueries(
    private val findCategoryUseCase: FindCategoryUseCase,
    private val categoryMapperApi: CategoryMapperApi,
) {

    @GetMapping("{category}")
    fun findProductById(@PathVariable category: String): CategoryInfoDto {
        return categoryMapperApi.entityToDto(findCategoryUseCase.executeByCategory(category))
    }
}
