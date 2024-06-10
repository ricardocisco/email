package br.com.fiap.email.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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



    private val _selectedItems = mutableStateListOf<Int>()
    val selectedItems: List<Int> = _selectedItems

    private val _isInEditMode = mutableStateOf(false)
    val isInEditMode: State<Boolean> = _isInEditMode

    fun toggleItemSelected(index: Int) {
        if (_selectedItems.contains(index)) {
            _selectedItems.remove(index)
        } else {
            _selectedItems.add(index)
        }
        _isInEditMode.value = _selectedItems.isNotEmpty()
    }

    fun clearSelectedItems() {
        _selectedItems.clear()
        _isInEditMode.value = false
    }
}