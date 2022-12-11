package com.example.nativedemo2.jpa

import org.springframework.data.repository.CrudRepository

/**
 * Querying interface to the databse.
 */
interface PersonRepository : CrudRepository<Person, Long>
