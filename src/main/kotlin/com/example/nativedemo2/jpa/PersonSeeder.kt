package com.example.nativedemo2.jpa

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Service

/**
 * Make sure we always have data to play around with.
 */
@Service
class PersonSeeder(private val repository: PersonRepository) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        val names = listOf("Frederic", "Thomas").toSortedSet()
        val inserted = repository.findAll().map { it.name }.toSortedSet()

        // Only run this when any of the desired names are missing.
        val seed = (names - inserted).map { Person(name = it) }

        repository.saveAll(seed)
    }
}
