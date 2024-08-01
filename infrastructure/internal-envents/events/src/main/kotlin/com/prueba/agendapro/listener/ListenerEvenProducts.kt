package com.prueba.agendapro.listener

import com.prueba.agendapro.dto.EventCategoryDto
import com.prueba.agendapro.mapper.EventCategoryMapperInternalEvents
import com.prueba.agendapro.usecase.commandusecase.statics.CategoryAddProductUseCase
import com.prueba.agendapro.usecase.commandusecase.statics.StrategyCategoryProduct
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class ListenerEvenProducts(
    private val categoryAddProductUseCase: CategoryAddProductUseCase,
    private val strategyCategoryProducts: List<StrategyCategoryProduct> = listOf(
        categoryAddProductUseCase
    ),
    private val mapper: EventCategoryMapperInternalEvents,
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @EventListener
    fun listenerProduct(event: EventCategoryDto) {
        log.info("Listener: ${event.toString()}")
        strategyCategoryProducts.first { event.action == it.getTypeAction() }
            .execute(mapper.dtoToEntity(event))
    }
}
