package com.prueba.agendapro.config

import com.prueba.agendapro.anotation.ValidationDomain
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.FilterType

@ComponentScan(
    basePackages = ["com.prueba.agendapro.validator"],
    includeFilters = [ComponentScan.Filter(
        type = FilterType.ANNOTATION,
        classes = [ValidationDomain::class]
    )]
)
@Configuration
open class ValidationDomainConfig() {
}

