package com.example.nativedemo2.jpa

import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * Controller to demo CRUD operations.
 *
 * <b>NOTE:</b> We aren't <i>reactor</i>-ing correctly here, but that's a-OK for this project... ;-)
 */
@RestController
class PersonController(private val repository: PersonRepository) {
    @GetMapping("/")
    fun hello(): Mono<String> = Mono.just("Hello!")

    @GetMapping("/person")
    fun list(): Flux<Person> = Flux.fromIterable(repository.findAll())

    @GetMapping("/person/{id}")
    fun single(@PathVariable id: Long): Mono<Person> =
        repository.findByIdOrNull(id)?.let { Mono.just(it) } ?: Mono.empty()

    @PostMapping("/person")
    fun create(@RequestBody person: Person): Mono<Person> = Mono.just(repository.save(person))
}
