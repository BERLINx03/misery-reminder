package com.example.miseryreminder.data.preferences

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PreferencesViewModel(
    private val applicationPreferences: ApplicationPreferences
): ViewModel() {
    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()
    private val _startDate = MutableStateFlow(System.currentTimeMillis())
    val startDate: StateFlow<Long> = _startDate.asStateFlow()
    private val _hustleDays = MutableStateFlow(0)
    val hustleDays: StateFlow<Int> = _hustleDays.asStateFlow()

    init {
        viewModelScope.launch {
            applicationPreferences.isDarkMode.collect { darkMode ->
                _isDarkMode.value = darkMode
            }
        }

        viewModelScope.launch {
            applicationPreferences.hasSound.collect { sound ->
                _hasSound.value = sound
            }
        }

        viewModelScope.launch {
            applicationPreferences.startDate.collect { date ->
                _startDate.value = date
            }
        }
        viewModelScope.launch {
            applicationPreferences.hustleDays.collect { days ->
                _hustleDays.value = days
            }
        }
    }

    fun toggleDarkMode() {
        viewModelScope.launch(Dispatchers.IO) {
            applicationPreferences.saveDarkMode(!_isDarkMode.value)
        }
    }

    fun updateStartDate(newStartDate: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            applicationPreferences.saveStartDate(newStartDate)
        }
    }
}