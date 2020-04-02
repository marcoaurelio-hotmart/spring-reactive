package com.hotncode.demo.springreactive

import com.hotncode.demo.springreactive.model.Address
import com.hotncode.demo.springreactive.model.User
import com.hotncode.demo.springreactive.repo.mongo.UserMongoRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.mongo.MongoReactiveRepositoriesAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.support.beans
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.data.mongodb.repository.config.ReactiveMongoRepositoryConfigurationExtension
import org.springframework.web.reactive.config.EnableWebFlux
import reactor.core.publisher.Flux
import java.time.Duration

@SpringBootApplication(exclude = [MongoReactiveRepositoriesAutoConfiguration::class])
@EnableWebFlux
class SpringReactiveApplication {

    @Bean
    fun init(userMongoRepository: UserMongoRepository) = CommandLineRunner {
        val address = Address(street = "Rua A", number = "1000", city = "BH", country = "BR")

        Flux.range(1, 100).delayElements(Duration.ofSeconds(2))
                .map { User(name="Matador $it", email="matador$it@hotmart.com", age = 36, address = address ) }
                .flatMap { userMongoRepository.save(it) }
                .subscribe()
    }

}

fun main(args: Array<String>) {
	runApplication<SpringReactiveApplication>(*args)



}


