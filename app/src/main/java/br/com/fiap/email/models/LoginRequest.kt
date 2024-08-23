package br.com.fiap.email.models

data class LoginRequest(
    val email: String,
    val password: String
)