package com.hotncode.demo.springreactive.controller

import com.hotncode.demo.springreactive.dto.UserFilterVO
import com.hotncode.demo.springreactive.model.User
import com.hotncode.demo.springreactive.repo.mongo.UserMongoRepository
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/user/v1")
class UserController(val userMongoRepository: UserMongoRepository) {


    @PostMapping
    fun create(@RequestBody user: Mono<User>, uriComponentsBuilder: UriComponentsBuilder)
            =  user.filterWhen {
                userMongoRepository.existsUserByEmail(it.email).map { !it }
            }.flatMap {
                userMongoRepository.save(it).map { userSaved ->
                    val uri = uriComponentsBuilder.path("/user/v1/${userSaved.id!!}").build()
                    ResponseEntity.created(uri.toUri()).body(userSaved)
                }
            }.switchIfEmpty(Mono.error(ResponseStatusException(HttpStatus.BAD_REQUEST, "This user already exists.")))

    @PostMapping("/bulk")
    fun createBulk(@RequestBody user : Flux<User>)
            = user.filterWhen {
                userMongoRepository.existsUserByEmail(it.email).log().map { exists -> !exists }
            }.flatMap { userMongoRepository.save(it) }


    @GetMapping("/{id}")
    fun findById(@PathVariable id : String)
            = userMongoRepository.findById(id).map { ResponseEntity.ok(it) }.defaultIfEmpty(ResponseEntity.notFound().build())

    @GetMapping
    fun find(filterVO: UserFilterVO)
            = filterVO.name?.let {userMongoRepository.findByNameStartsWith(filterVO.name)} ?: userMongoRepository.findAll()

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody user: User)
            = userMongoRepository.findById(id).flatMap {
                it.address = user.address
                it.age = user.age
                it.name = user.name
            userMongoRepository.save(it)
            }.map { ResponseEntity.ok(it) }.defaultIfEmpty(ResponseEntity.notFound().build())

    @GetMapping("/tail", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun findUsers() = userMongoRepository.findWithTailableCursorBy()
}
