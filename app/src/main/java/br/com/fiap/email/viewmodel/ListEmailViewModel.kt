package br.com.fiap.email.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ListEmailViewModel : ViewModel() {
    private val _favoriteEmails = mutableStateOf<Set<Int>>(setOf())

    val favoriteEmails: Set<Int>
        get() = _favoriteEmails.value

    fun toggleFavorite(emailIndex: Int) {
        val updatedFavorites = _favoriteEmails.value.toMutableSet()

        if (updatedFavorites.contains(emailIndex)) {
            updatedFavorites.remove(emailIndex)
        } else {
            updatedFavorites.add(emailIndex)
        }

        _favoriteEmails.value = updatedFavorites
    }

    fun isFavorite(emailIndex: Int): Boolean {
        return favoriteEmails.contains(emailIndex)
    }
}