package com.hotncode.demo.springreactive.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Document
data class User(@Id val id: String?,
                var name: String,
                var email: String,
                var age: Int,
                var address: Address)

data class Address(val street: String,
                   val number: String,
                   val city: String,
                   val country: String)


@Table("user")
data class UserMysql(@Id val id: Long?,
                     var name: String,
                     var email: String,
                     var age: Int,
                     var address: Address?)

@Table("address")
data class AddressMysql(val id: Long?,
                   val street: String,
                   val number: String,
                   val city: String,
                   val country: String)
