package com.example.mp0901

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

enum class DarkModeOption {
    SYSTEM,  // 시스템 설정 따르기
    LIGHT,   // 라이트 모드
    DARK     // 다크 모드
}

private val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private val context = application.applicationContext
    private val dataStore = context.dataStore

    companion object {
        private val KEY_DARK_MODE = stringPreferencesKey("dark_mode")
        private val KEY_NOTIFICATION = booleanPreferencesKey("notification")
        private val KEY_LANGUAGE = stringPreferencesKey("language")
    }

    private val defaultUiState = SettingsUiState()

    private val _uiState = MutableStateFlow(defaultUiState)
    val uiState: StateFlow<SettingsUiState> = _uiState

    init {
        viewModelScope.launch {
            dataStore.data
                .map { prefs ->
                    SettingsUiState(
                        darkModeOption = DarkModeOption.valueOf(
                            prefs[KEY_DARK_MODE] ?: DarkModeOption.SYSTEM.name
                        ),
                        isNotificationEnabled = prefs[KEY_NOTIFICATION] ?: true,
                        selectedLanguage = prefs[KEY_LANGUAGE] ?: "한국어"
                    )
                }
                .collect { _uiState.value = it }
        }
    }

    fun setDarkModeOption(option: DarkModeOption) {
        viewModelScope.launch {
            dataStore.edit { prefs ->
                prefs[KEY_DARK_MODE] = option.name
            }
        }
    }

    fun toggleNotification() {
        viewModelScope.launch {
            dataStore.edit { prefs ->
                val current = prefs[KEY_NOTIFICATION] ?: true
                prefs[KEY_NOTIFICATION] = !current
            }
        }
    }

    fun selectLanguage(language: String) {
        viewModelScope.launch {
            dataStore.edit { prefs ->
                prefs[KEY_LANGUAGE] = language
            }
        }
    }

    fun logout() {
        // 필요 시 로그아웃 처리 추가
        viewModelScope.launch {
            dataStore.edit { it.clear() }
        }
    }
}

data class SettingsUiState(
    val darkModeOption: DarkModeOption = DarkModeOption.SYSTEM,
    val isNotificationEnabled: Boolean = true,
    val selectedLanguage: String = "한국어"
)
