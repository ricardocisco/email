package br.com.fiap.email.models

data class AuthResponse(
    val token: String,
    val user: User
)