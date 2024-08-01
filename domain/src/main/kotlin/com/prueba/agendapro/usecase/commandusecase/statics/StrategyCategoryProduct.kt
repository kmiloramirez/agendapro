package com.prueba.agendapro.usecase.commandusecase.statics

import com.prueba.agendapro.entity.EventCategory
import com.prueba.agendapro.entity.enums.Action

interface StrategyCategoryProduct {

    fun getTypeAction(): Action
    fun execute(eventCategory: EventCategory)
}
