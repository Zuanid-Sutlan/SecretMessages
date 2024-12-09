package com.example.secretmessagesencrypter.precentation.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.secretmessagesencrypter.utils.SettingsManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(private val settingsManager: SettingsManager) : ViewModel() {

    private val _darkMode = MutableStateFlow(3)
    val darkMode: StateFlow<Int> get() = _darkMode.asStateFlow()

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> get() = _username.asStateFlow()

    private val _encryptionKey = MutableStateFlow("")
    val encryptionKey: StateFlow<String> get() = _encryptionKey.asStateFlow()

    init {
        loadSettings()
        if (_username.value.isEmpty()) {

        }
    }

    fun loadSettings() {
        viewModelScope.launch {
            settingsManager.darkMode.collect { _darkMode.value = it }
            settingsManager.username.collect { _username.value = it }
            settingsManager.encryptionKey.collect { _encryptionKey.value = it }
        }
    }

    fun setTheme(appearanceMode: Int) {
        viewModelScope.launch {
            settingsManager.setTheme(appearanceMode)
        }
    }

    fun setUsername(name: String) {
        viewModelScope.launch {
            settingsManager.setUsername(name)
        }
    }

    fun setEncryptionKey(key: String) {
        viewModelScope.launch {
            settingsManager.setEncryptionKey(key)
        }
    }
}
