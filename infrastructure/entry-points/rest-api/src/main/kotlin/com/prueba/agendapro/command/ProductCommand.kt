package com.prueba.agendapro.command

import com.prueba.agendapro.dto.ProductCreateRequest
import com.prueba.agendapro.dto.ProductDto
import com.prueba.agendapro.dto.ProductUpdateRequest
import com.prueba.agendapro.mapper.ProductMapperApi
import com.prueba.agendapro.usecase.commandusecase.product.CrateProductUseCase
import com.prueba.agendapro.usecase.commandusecase.product.DeleteProductUseCase
import com.prueba.agendapro.usecase.commandusecase.product.UpdateProductUseCase
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController("productCommandV1")
@RequestMapping(value = ["v1/products"])
class ProductCommand(
    private val productMapperApi: ProductMapperApi,
    private val createProductUseCase: CrateProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
) {

    @PostMapping
    fun createProduct(@RequestBody productCreateRequest: ProductCreateRequest): ProductDto {
        return productMapperApi.entityToDto(
            createProductUseCase.execute(
                productMapperApi.createProductToEntity(
                    productCreateRequest
                )
            )
        )
    }

    @PatchMapping("{productId}")
    fun updateProduct(
        @PathVariable productId: UUID,
        @RequestBody productUpdateRequest: ProductUpdateRequest,
    ): ProductDto {
        return productMapperApi.entityToDto(
            updateProductUseCase.execute(
                productId,
                productMapperApi.updateProductToUpdateEntity(
                    productUpdateRequest
                )
            )
        )
    }

    @DeleteMapping("{productId}")
    fun deleteProduct(@PathVariable productId: UUID) {
        deleteProductUseCase.execute(productId)
    }
}
