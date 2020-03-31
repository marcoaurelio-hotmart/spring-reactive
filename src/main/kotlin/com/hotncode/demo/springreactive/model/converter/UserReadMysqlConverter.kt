package com.hotncode.demo.springreactive.model.converter

import com.hotncode.demo.springreactive.model.AddressMysql
import com.hotncode.demo.springreactive.model.UserMysql
import io.r2dbc.spi.Row
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter

@ReadingConverter
class UserReadMysqlConverter: Converter<Row, UserMysql> {

    override fun convert(source: Row) : UserMysql {
        var address : AddressMysql? = null
        try {
            source.get("address_id")?.let {
                        address = AddressMysql(id = it as Long,
                        street = source.get("street", String::class.java)!!,
                        number = source.get("number", String::class.java)!!,
                        city = source.get("city", String::class.java)!!,
                        country = source.get("country", String::class.java)!!)

            }
        } catch (e: NoSuchElementException) {
            // do nothing, query without address
        }


        val user = UserMysql(source.get("id", Long::class.java),
                            source.get("name", String::class.java)!!,
                            source.get("email", String::class.java)!!,
                            source.get("age", Int::class.java)!!,
                            address)

        return user
    }

}
