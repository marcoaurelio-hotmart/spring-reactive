package com.hotncode.demo.springreactive.controller

import com.hotncode.demo.springreactive.dto.UserFilterVO
import com.hotncode.demo.springreactive.model.UserMysql
import com.hotncode.demo.springreactive.repo.r2dbc.UserMysqlRepository
import com.hotncode.demo.springreactive.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Flux


@RestController
@RequestMapping("/user/v2")
class UserV2Controller(val userService: UserService) {


    @PostMapping
    fun create(@RequestBody user: UserMysql, uriComponentsBuilder: UriComponentsBuilder)
            = userService.save(user).map { userSaved ->
                val uri = uriComponentsBuilder.path("/user/v2/${userSaved.id!!}").build()
                ResponseEntity.created(uri.toUri()).body(userSaved)
            }

    @PostMapping("/bulk")
    fun createBulk(@RequestBody user : Flux<UserMysql>) = userService.save(user)

    @GetMapping("/{id}")
    fun findById(@PathVariable id : Long)
            = userService.findById(id).map { ResponseEntity.ok(it) }

    @GetMapping
    fun find(filterVO: UserFilterVO) = userService.find(filterVO)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody user: UserMysql)
            = userService.findById(id).flatMap {
                it.age = user.age
                it.name = user.name
                user.address?.let { addressMysql -> it.address = addressMysql }
                userService.save(it)
            }.map { ResponseEntity.ok(it) }.defaultIfEmpty(ResponseEntity.notFound().build())
}
