package com.prueba.agendapro.builder

import com.prueba.agendapro.entity.EventCategory
import com.prueba.agendapro.entity.enums.Action
import com.prueba.agendapro.entity.enums.EntityEvent
import java.util.UUID

class EventCategoryBuilder(
    private var id: UUID = UUID.randomUUID(),
    private var entityEvent: EntityEvent = EntityEvent.produtc,
    private var action: Action = Action.add_product,
    private var oldCategory: String? = null,
) {
    fun id(id: UUID) = apply { this.id = id }
    fun entityEvent(entityEvent: EntityEvent) = apply { this.entityEvent = entityEvent }
    fun action(action: Action) = apply { this.action = action }
    fun oldCategory(oldCategory: String?) = apply { this.oldCategory = oldCategory }
    fun build() = EventCategory(id, entityEvent, action, oldCategory)
}
