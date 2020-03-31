package com.hotncode.demo.springreactive.config

import com.mongodb.reactivestreams.client.MongoClients
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@Configuration
@EnableReactiveMongoRepositories("com.hotncode.demo.springreactive.repo.mongo")
class ReactiveMongoConfig : AbstractReactiveMongoConfiguration() {

    @Bean
    fun mongoClient() = MongoClients.create()

    override fun getDatabaseName() = "reactive-sample"
}
