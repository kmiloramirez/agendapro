package com.prueba.agendapro.adapter.event

import com.prueba.agendapro.entity.EventCategory

fun interface StaticsEventPublish {
    fun publishEventCategory(eventCategory: EventCategory)
}
