package com.hotncode.demo.springreactive.controller

import com.hotncode.demo.springreactive.dto.UserFilterVO
import com.hotncode.demo.springreactive.model.User
import com.hotncode.demo.springreactive.model.UserMysql
import com.hotncode.demo.springreactive.repo.r2dbc.UserMysqlRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/user/v2")
class UserV2Controller(val userRepository: UserMysqlRepository) {

    @PostMapping
    fun create(@RequestBody user: Mono<UserMysql>, uriComponentsBuilder: UriComponentsBuilder)
            =  user.flatMap {
                userRepository.save(it).map { userSaved ->
                    val uri = uriComponentsBuilder.path("/user/v2/${userSaved.id!!}").build()
                    ResponseEntity.created(uri.toUri()).body(userSaved)
                }
            }.switchIfEmpty(Mono.error(ResponseStatusException(HttpStatus.BAD_REQUEST, "This user already exists.")))

    @GetMapping("/{id}")
    fun findById(@PathVariable id : Long)
            = userRepository.findById(id).map { ResponseEntity.ok(it) }.defaultIfEmpty(ResponseEntity.notFound().build())

    @GetMapping
    fun find(filterVO: UserFilterVO)
            = filterVO.name?.let {userRepository.findByNameStartsWith("${filterVO.name}%")} ?: userRepository.findAll()

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody user: UserMysql)
            = userRepository.findById(id).flatMap {
                it.address = user.address
                it.age = user.age
                it.name = user.name
                userRepository.save(it)
            }.map { ResponseEntity.ok(it) }.defaultIfEmpty(ResponseEntity.notFound().build())
}
