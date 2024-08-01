package com.prueba.agendapro.mapper

import com.prueba.agendapro.dto.EventCategoryDto
import com.prueba.agendapro.entity.EventCategory
import org.springframework.stereotype.Component

@Component
class EventCategoryMapperInternalEvents {

    fun entityToDto(eventCategory: EventCategory): EventCategoryDto {
        return EventCategoryDto(
            id = eventCategory.id,
            entityEvent = eventCategory.entityEvent,
            action = eventCategory.action,
            oldCategory = eventCategory.oldCategory

        )
    }

    fun dtoToEntity(eventCategoryDto: EventCategoryDto): EventCategory {
        return EventCategory(
            id = eventCategoryDto.id,
            entityEvent = eventCategoryDto.entityEvent,
            action = eventCategoryDto.action,
            oldCategory = eventCategoryDto.oldCategory

        )
    }
}
