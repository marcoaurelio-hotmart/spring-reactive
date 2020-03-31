package com.hotncode.demo.springreactive.model.converter

import com.hotncode.demo.springreactive.model.UserMysql
import io.r2dbc.spi.Row
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter

@ReadingConverter
class UserReadMysqlConverter: Converter<Row, UserMysql> {

    override fun convert(source: Row)
            = UserMysql(source.get("id", Long::class.java),
                        source.get("name", String::class.java)!!,
                        source.get("email", String::class.java)!!,
                        source.get("age", Int::class.java)!!, null)
}
