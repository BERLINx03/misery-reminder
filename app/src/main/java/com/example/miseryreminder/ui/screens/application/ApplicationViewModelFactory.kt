package com.example.miseryreminder.ui.screens.application

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.miseryreminder.data.database.ApplicationDao

class ApplicationViewModelFactory(
    private val applicationDao: ApplicationDao
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ApplicationViewModel::class.java)){
            return ApplicationViewModel(applicationDao = applicationDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}