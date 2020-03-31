package com.hotncode.demo.springreactive.model.converter

import com.hotncode.demo.springreactive.model.UserMysql
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.r2dbc.mapping.OutboundRow
import org.springframework.data.r2dbc.mapping.SettableValue

@WritingConverter
class UserWriteMysqlConverter: Converter<UserMysql, OutboundRow> {

    override fun convert(source: UserMysql): OutboundRow {
        val out = OutboundRow()
        source.id?.let { out.put("id", SettableValue.from(it)) }
        out.put("name", SettableValue.from(source.name))
        out.put("email", SettableValue.from(source.email))
        out.put("age", SettableValue.from(source.age))
        source.address?.let {
            it.id?.let { id -> out.put("address_id", SettableValue.from(id)) }
        }

        return out
    }
}
