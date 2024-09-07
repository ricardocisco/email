package br.com.fiap.email.models

data class PasswordReset (
    val email: String,
    val newPassword: String
)


