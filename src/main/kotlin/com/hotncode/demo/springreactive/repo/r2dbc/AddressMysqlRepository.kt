package com.hotncode.demo.springreactive.repo.r2dbc

import com.hotncode.demo.springreactive.model.AddressMysql
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository

@Repository
interface AddressMysqlRepository : R2dbcRepository<AddressMysql, Long> {
}
