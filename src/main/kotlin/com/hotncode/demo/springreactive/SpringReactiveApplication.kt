package com.hotncode.demo.springreactive

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@EnableWebFlux
class SpringReactiveApplication

fun main(args: Array<String>) {
	runApplication<SpringReactiveApplication>(*args)
}
