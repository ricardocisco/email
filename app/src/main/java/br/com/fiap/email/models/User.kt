package br.com.fiap.email.models

data class User(
    val id: String,
    val nome: String,
    val email: String,
    val preferences: Preferences = Preferences(),
    val emails: Emails = Emails()
)

data class Preferences(
    val theme: String = "light",
)

data class Emails(
    val sent: List<Email> = emptyList(),
    val received: List<ReceivedEmail> = emptyList()
)

data class Email(
    val sentEmail: String,
    val sentNome: String,
    val subject: String,
    val body: String,
    val sentAt: String,
    val isSpam: Boolean = false
)

data class ReceivedEmail(
    val emailId: String,
    val receiveEmail: String,
    val receiveNome: String,
    val subject: String,
    val body: String,
    val isSpam: Boolean
)