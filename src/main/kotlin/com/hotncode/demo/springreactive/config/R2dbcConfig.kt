package com.hotncode.demo.springreactive.config

import org.springframework.boot.autoconfigure.r2dbc.ConnectionFactoryBuilder
import org.springframework.boot.autoconfigure.r2dbc.EmbeddedDatabaseConnection
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.ReactiveMongoRepositoryConfigurationExtension
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@Configuration
@EnableR2dbcRepositories("com.hotncode.demo.springreactive.repo.r2dbc")
class R2dbcConfig(val r2dbcProperties: R2dbcProperties): AbstractR2dbcConfiguration() {

    override fun connectionFactory()
        = ConnectionFactoryBuilder.of(r2dbcProperties) { EmbeddedDatabaseConnection.NONE }.build()
}
