package br.com.fiap.email.models

data class RegisterRequest(
    val nome: String,
    val email: String,
    val password: String
)