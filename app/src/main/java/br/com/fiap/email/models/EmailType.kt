package br.com.fiap.email.models

sealed class EmailType {
    object Sent : EmailType()
    object Received : EmailType()
    object Archived : EmailType()
    object Trash : EmailType()
}