package com.example.miseryreminder.ui.screens.application

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miseryreminder.database.ApplicationDao
import com.example.miseryreminder.database.ApplicationEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ApplicationViewModel(
    val applicationDao: ApplicationDao
): ViewModel() {
    private val _applications = MutableStateFlow<List<ApplicationEntity>>(emptyList())
    val applications = _applications.asStateFlow()

    init {
        getAllApplications()
    }
    fun getAllApplications() {
        viewModelScope.launch(Dispatchers.IO) {
            applicationDao.getAllApplications().collectLatest { apps ->
                withContext(Dispatchers.Main){
                    _applications.value = apps
                }
            }
        }
    }

    fun upsertApplication(application: ApplicationEntity){
        viewModelScope.launch(Dispatchers.IO) {
            applicationDao.upsertApplication(application)
        }
        getAllApplications()
    }
}