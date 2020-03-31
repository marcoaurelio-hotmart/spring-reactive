package com.hotncode.demo.springreactive.repo.r2dbc

import com.hotncode.demo.springreactive.model.UserMysql
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface UserMysqlRepository : R2dbcRepository<UserMysql, Long> {

    @Query("select u.id, u.name, u.email, u.age, u.address_id, a.street, a.number, a.city, a.country from user u left join address a on u.address_id = a.id where u.name like :name")
    fun findByNameStartsWith(name: String) : Flux<UserMysql>

    @Query("select u.id, u.name, u.email, u.age, u.address_id, a.street, a.number, a.city, a.country from user u left join address a on u.address_id = a.id")
    fun findAllWithAddress() : Flux<UserMysql>

    @Query("select u.id, u.name, u.email, u.age, u.address_id, a.street, a.number, a.city, a.country from user u left join address a on u.address_id = a.id where u.id = :id")
    fun findByIdWithAddress(id: Long) : Mono<UserMysql>
}
