package br.com.fiap.email.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.email.models.UpdateFontRequest
import br.com.fiap.email.models.UpdateLanguageRequest
import br.com.fiap.email.models.UpdateThemeRequest
import br.com.fiap.email.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale

class ThemeViewModel() : ViewModel() {

    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme

    private val _fontSize = MutableStateFlow(16f)
    val fontSize: StateFlow<Float> = _fontSize

    private val _updateLanguage = MutableStateFlow("")
    val updateLanguage: StateFlow<String> = _updateLanguage

    fun toggleTheme(userId: String) {
        viewModelScope.launch {
            val newTheme = !_isDarkTheme.value
            _isDarkTheme.value = newTheme
            ApiClient.authService.updateTheme(userId, UpdateThemeRequest(theme = if (newTheme) "dark" else "light"))
        }
    }

    fun updateFontSize(userId: String) {
        viewModelScope.launch {
            try {
                val newSize = _fontSize.value
                ApiClient.authService.updateFontSize(userId, UpdateFontRequest(fontSize = newSize))
            } catch (e: Exception){
                println("Erro:${e.message}")
            }
        }
    }

    fun setLocalFontSize(newSize: Float) {
        _fontSize.value = newSize
    }

    fun updateNewLanguage(userId: String){
        viewModelScope.launch {
            val newLanguage = _updateLanguage.value
            ApiClient.authService.updateLanguage(userId, UpdateLanguageRequest(language = newLanguage))
        }
    }

    fun setLocalLanguage(newLanguage: String) {
        _updateLanguage.value = newLanguage
    }
}