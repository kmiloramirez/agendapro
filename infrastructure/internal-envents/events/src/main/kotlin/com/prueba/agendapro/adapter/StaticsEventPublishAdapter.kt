package com.prueba.agendapro.adapter

import com.prueba.agendapro.adapter.event.StaticsEventPublish
import com.prueba.agendapro.entity.EventCategory
import com.prueba.agendapro.mapper.EventCategoryMapperInternalEvents
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class StaticsEventPublishAdapter(
    private val eventCategoryMapperInternalEvents: EventCategoryMapperInternalEvents,
    private val applicationEventPublisher: ApplicationEventPublisher,
) : StaticsEventPublish {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun publishEventCategory(eventCategory: EventCategory) {

        log.info("Publishing event category for: ${eventCategory.entityEvent} id: ${eventCategory.id} action: ${eventCategory.action}")
        val eventToPublish = eventCategoryMapperInternalEvents.entityToDto(eventCategory)
        applicationEventPublisher.publishEvent(eventToPublish)
    }
}
