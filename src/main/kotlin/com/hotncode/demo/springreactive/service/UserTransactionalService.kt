package com.hotncode.demo.springreactive.service

import com.hotncode.demo.springreactive.model.UserMysql
import com.hotncode.demo.springreactive.repo.r2dbc.AddressMysqlRepository
import com.hotncode.demo.springreactive.repo.r2dbc.UserMysqlRepository
import org.springframework.data.r2dbc.connectionfactory.R2dbcTransactionManager
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.reactive.TransactionalOperator
import reactor.core.publisher.Mono

@Service
class UserTransactionalService(override val userRepository: UserMysqlRepository,
                               override val addressRepository: AddressMysqlRepository,
                               val transactionManager: R2dbcTransactionManager) : UserService(userRepository, addressRepository) {

    @Transactional
    fun saveWithAnnotation(user : UserMysql)
        = user.address?.let {
            addressRepository.save(it).log().map { addressSaved ->  user.address = addressSaved }.thenReturn(user)
                    .flatMap { userMysql ->  userRepository.save(userMysql).log() }
        } ?: run {
            userRepository.save(user)
        }

    override fun save(user : UserMysql) : Mono<UserMysql> {
        val transaction = TransactionalOperator.create(transactionManager)

        return user.address?.let {
            addressRepository.save(it).log().map { addressSaved ->  user.address = addressSaved }.thenReturn(user)
                    .flatMap { userMysql ->  userRepository.save(userMysql).log() }.`as`(transaction::transactional)
        } ?: run {
            userRepository.save(user)
        }
    }

}
