package com.example.miseryreminder.data.preferences

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PreferencesViewModelFactory(
    private val applicationPreferences: ApplicationPreferences
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PreferencesViewModel::class.java)){
            return PreferencesViewModel(applicationPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}