package com.prueba.agendapro.dto

import com.prueba.agendapro.entity.enums.EntityEvent
import com.prueba.agendapro.entity.enums.Action
import java.util.UUID

class EventCategoryDto(
    val id: UUID,
    val entityEvent: EntityEvent,
    val action: Action,
    val oldCategory: String? = null,
)
