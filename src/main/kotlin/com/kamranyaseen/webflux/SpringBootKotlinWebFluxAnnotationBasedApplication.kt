package com.kamranyaseen.webflux

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
@SpringBootApplication
class SpringBootKotlinWebFluxAnnotationBasedApplication

fun main(args: Array<String>) {
    runApplication<SpringBootKotlinWebFluxAnnotationBasedApplication>(*args)
}