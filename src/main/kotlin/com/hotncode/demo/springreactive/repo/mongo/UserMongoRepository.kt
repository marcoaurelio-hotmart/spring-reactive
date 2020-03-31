package com.hotncode.demo.springreactive.repo.mongo

import com.hotncode.demo.springreactive.model.User
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface UserMongoRepository : ReactiveCrudRepository<User, String> {

    fun findByNameStartsWith(name: String): Flux<User>

}
