package br.com.fiap.email.models

data class MoveEmailsRequest(
    val emailIds: List<String>,
    val emailType: String
)