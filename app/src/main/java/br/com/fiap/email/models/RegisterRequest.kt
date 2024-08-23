package br.com.fiap.email.models

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)