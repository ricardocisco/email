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
    val emailId: String,
    val recipient: String,
    val subject: String,
    val body: String,
    val sentAt: String,
    val receivedAt: String? = null,
    val isSpam: Boolean = false
)

data class ReceivedEmail(
    val emailId: String,
    val sender: String,
    val subject: String,
    val body: String,
    val receivedAt: String,
    val isSpam: Boolean
)