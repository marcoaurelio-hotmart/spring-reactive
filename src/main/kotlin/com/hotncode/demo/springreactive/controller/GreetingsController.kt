package com.hotncode.demo.springreactive.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/greetings")
class GreetingsController {


    @GetMapping
    fun greetings(@RequestParam(defaultValue = "Spring") name : String )
            = Mono.just("Hello $name")
}
