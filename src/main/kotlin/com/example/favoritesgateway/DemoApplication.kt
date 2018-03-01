package com.example.favoritesgateway

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = [FavoritesGatewayController::class])
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}


@RestController
@Configuration
class FavoritesGatewayController {

    @PostMapping("/favorites/user/{userId}/country/{country}/language/{language}/graphql")
    fun favorites(
            @PathVariable("userId") userId: String,
            @PathVariable("country") country: String,
            @PathVariable("language") language: String): Any {
        println(userId)
        println(country)
        println(language)
//        val result = Meest.parse(rawRequest)
//                .flatMap { meest.request(it) }
//                .bimap({
//                    ResponseEntity.status(it.first().code).body(map(it))
//                }, {
//                    ResponseEntity.ok().body(it)
//                })
//        if (result.isLeft()) {
//            return result.swap().get()
//        } else {
//            return result.get()
//        }
        throw IllegalArgumentException("")
    }

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun illegalArgumentException() {
    }
}