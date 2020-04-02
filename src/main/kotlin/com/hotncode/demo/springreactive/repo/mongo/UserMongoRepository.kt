package com.hotncode.demo.springreactive.repo.mongo

import com.hotncode.demo.springreactive.model.User
import org.springframework.data.mongodb.repository.Tailable
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface UserMongoRepository : ReactiveCrudRepository<User, String> {

    fun findByNameStartsWith(name: String): Flux<User>

    fun existsUserByEmail(email : String) : Mono<Boolean>

    @Tailable
    fun findWithTailableCursorBy(): Flux<User>

}
