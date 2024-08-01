package com.prueba.agendapro.entity

import com.prueba.agendapro.entity.enums.EntityEvent
import com.prueba.agendapro.entity.enums.Action
import java.util.UUID

class EventCategory(
    val id: UUID,
    val entityEvent: EntityEvent,
    val action: Action,
    val oldCategory: String? = null,
)
