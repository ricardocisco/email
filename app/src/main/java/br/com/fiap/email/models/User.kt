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
    val language: String = "br",
    val fontSize: Float  = 16f,
    val emailSortOrder: String = "date"
)

data class Emails(
    val sent: List<Email> = emptyList(),
    val received: List<ReceivedEmail> = emptyList(),
    val archived: List<ArchivedEmail> = emptyList(),
    val trash: List<TrashEmail> = emptyList()
)

data class Email(
    val emailId: String,
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
    val receivedAt: String,
    val subject: String,
    val body: String,
    val isSpam: Boolean
)

data class ArchivedEmail (
    val emailId: String,
    val emailType: String,
    val emailDataBase: EmailDataBase
)

data class TrashEmail (
    val emailId: String,
    val emailType: String,
    val emailDataBase: EmailDataBase
)

data class EmailDataBase (
    val emailId: String,
    val subject: String?,
    val body: String?,
    val sentEmail: String?,
    val sentNome: String?,
    val sentAt: String?,
    val receiveEmail: String?,
    val receiveNome: String?,
    val receivedAt: String?,
    val isSpam: Boolean?
)