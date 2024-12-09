package com.example.secretmessagesencrypter.utils

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Create a DataStore instance
private val Context.dataStore by preferencesDataStore(name = "app_settings")

class SettingsManager(private val context: Context) {

    companion object {
        val DARK_MODE = intPreferencesKey("dark_mode")
        val USERNAME = stringPreferencesKey("username")
        val ENCRYPTION_KEY = stringPreferencesKey("encryption_key")
    }

    // Get dark mode setting
    val darkMode: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[DARK_MODE] ?: 3
        }

    // Get username
    val username: Flow<String> = context.dataStore.data
        .map { preferences ->
            val value = preferences[USERNAME] ?: ""
            Log.i("username", "getUsername: $value")
            value
        }

    // Get font size
    val encryptionKey: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[ENCRYPTION_KEY] ?: ""
        }

    // Save dark mode setting
    suspend fun setTheme(appearanceMode: Int) {
        context.dataStore.edit { preferences ->
            preferences[DARK_MODE] = appearanceMode
        }
    }

    // Save username
    suspend fun setUsername(username: String) {
        context.dataStore.edit { preferences ->
            preferences[USERNAME] = username
            Log.i("username", "setUsername: $username")
        }
    }

    // Save font size
    suspend fun setEncryptionKey(key: String) {
        context.dataStore.edit { preferences ->
            preferences[ENCRYPTION_KEY] = key
        }
    }
}
