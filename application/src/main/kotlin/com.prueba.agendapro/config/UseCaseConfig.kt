package com.prueba.agendapro.config

import com.prueba.agendapro.anotation.UseCase
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.FilterType
import org.springframework.stereotype.Component

@ComponentScan(
    basePackages = ["com.prueba.agendapro.usecase"],
    includeFilters = [ComponentScan.Filter(
        type = FilterType.ANNOTATION,
        classes = [UseCase::class]
    )]
)
@Configuration
@Component
open class UseCaseConfig


