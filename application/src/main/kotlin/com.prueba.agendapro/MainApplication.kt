package com.prueba.agendapro

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.prueba.agendapro.**"])
@ConfigurationPropertiesScan
open class MainApplication

fun main(args: Array<String>) {
    runApplication<MainApplication>(*args)
}
