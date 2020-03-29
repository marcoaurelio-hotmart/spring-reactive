package com.hotncode.demo.springreactive.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserter
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import kotlin.random.Random

@Configuration
class RouterConfig {

    @Bean
    fun router(handler: RequestsHandler) = router {
        "/handler".nest {
            GET("/greetings", handler::get)
            GET("/flux/sample", handler::fluxSample)
            POST("/greetings", handler::post)

        }
    }
}

@Component
class RequestsHandler {

    fun get(request: ServerRequest)
            = Mono.just(request.queryParam("name").orElse("Spring")).flatMap {
                ok().contentType(MediaType.TEXT_PLAIN).body(BodyInserters.fromValue("Hello $it"))
            }

    fun post(request: ServerRequest)
            = request.bodyToMono(SampleDto::class.java).log().flatMap {
                ok().build()
            }

    fun fluxSample(request: ServerRequest)
            = ok().body(Flux.range(1,10).log().map { SampleDto(name = "Marco $it", age = Random.nextInt(18, 45)) }.log(), SampleDto::class.java)

}


data class SampleDto(val name: String, val age:Int)
