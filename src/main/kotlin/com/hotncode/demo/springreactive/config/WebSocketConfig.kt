package com.hotncode.demo.springreactive.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter
import reactor.core.publisher.EmitterProcessor

@Configuration
class WebSocketConfig {

    @Bean
    fun webSocketHandlerAdapter() = WebSocketHandlerAdapter()

    @Bean
    fun handlerMapping(eventPublisher: EmitterProcessor<String>): HandlerMapping {
        val simpleMapping = SimpleUrlHandlerMapping()
        simpleMapping.urlMap = mapOf("/ws/user" to WSHandler(eventPublisher))
        return simpleMapping
    }

    @Bean
    fun eventPubliher() = EmitterProcessor.create<String>()
}



@Component
class WSHandler(val eventPublisher: EmitterProcessor<String>) : WebSocketHandler {

    override fun handle(session: WebSocketSession)
            = session.send(eventPublisher.map { session.textMessage(it) })

}
