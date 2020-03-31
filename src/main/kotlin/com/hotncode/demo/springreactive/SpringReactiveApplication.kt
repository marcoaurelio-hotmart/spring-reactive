package com.hotncode.demo.springreactive

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.mongo.MongoReactiveRepositoriesAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.data.mongodb.repository.config.ReactiveMongoRepositoryConfigurationExtension
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication(exclude = [MongoReactiveRepositoriesAutoConfiguration::class])
@EnableWebFlux
class SpringReactiveApplication

fun main(args: Array<String>) {
	runApplication<SpringReactiveApplication>(*args)
}


