package com.hotncode.demo.springreactive.repo.r2dbc

import com.hotncode.demo.springreactive.model.UserMysql
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface UserMysqlRepository : R2dbcRepository<UserMysql, Long> {

    @Query("select u.id, u.name, u.email, u.age from user where name like :name")
    fun findByNameStartsWith(name: String) : Flux<UserMysql>
}
