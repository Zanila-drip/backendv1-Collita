package com.desarrollomovil.demo12

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.config.EnableMongoAuditing

@SpringBootApplication
@EnableMongoAuditing
class Demo12Application

fun main(args: Array<String>) {
    runApplication<Demo12Application>(*args)
}
