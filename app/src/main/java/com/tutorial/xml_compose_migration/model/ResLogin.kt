package com.tutorial.xml_compose_migration.model

data class ResLogin(
    val email: String,
    val module: List<Any>,
    val role: String,
    val status: Int,
    val token: String,
    val username: String
)