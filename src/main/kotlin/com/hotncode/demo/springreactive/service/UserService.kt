package com.hotncode.demo.springreactive.service

import com.hotncode.demo.springreactive.dto.UserFilterVO
import com.hotncode.demo.springreactive.model.UserMysql
import com.hotncode.demo.springreactive.repo.r2dbc.AddressMysqlRepository
import com.hotncode.demo.springreactive.repo.r2dbc.UserMysqlRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.EmitterProcessor
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class UserService(val userRepository: UserMysqlRepository,
                  val addressRepository: AddressMysqlRepository,
                  val eventPublisher: EmitterProcessor<String>) {


    fun save(user : Flux<UserMysql>) = user.flatMap { this.save(it) }

    fun save(user : UserMysql)
        = user.address?.let {
            addressRepository.save(it).log().map { addressSaved ->  user.address = addressSaved }.thenReturn(user)
                    .flatMap { userMysql ->  userRepository.save(userMysql).log() }.doOnNext { user -> eventPublisher.onNext(user.toString()) }
        } ?: run {
            userRepository.save(user).doOnNext { user -> eventPublisher.onNext(user.toString()) }
        }

    fun findById(id : Long)
            = userRepository.findByIdWithAddress(id).switchIfEmpty(Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND, "user not found")))

    fun find(filterVO: UserFilterVO)
            = filterVO.name?.let {userRepository.findByNameStartsWith("${filterVO.name}%")} ?: userRepository.findAllWithAddress()


}
