package br.com.fiap.email.models

data class UpdateThemeRequest(
    val theme: String
)

data class UpdateFontRequest(
    val fontSize: Float
)

data class UpdateEmailSortOrderRequest(
    val emailSortOrder: String
)

data class UpdateLanguageRequest(
    val language: String
)