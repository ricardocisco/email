package br.com.fiap.email.models

data class MoveEmailsRequest(
    val emailIds: List<String>,
    val emailType: String
)

data class MoveArchivedRequest(
    val emailIds: List<String>
)

data class DeleteEmailsRequest(
    val userId: String,
    val emailIds: List<String>
)