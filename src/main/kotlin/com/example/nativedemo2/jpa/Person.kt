package com.example.nativedemo2.jpa

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

/**
 * Simple entity for demoing CRUD operations.
 */
@Entity
data class Person(@Id @GeneratedValue val id: Long? = null, val name: String)
