package com.example.miseryreminder.data.preferences

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map


class ApplicationPreferences(
    val dataStore: DataStore<Preferences>
) {

    val isDarkMode: Flow<Boolean> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e("data store", "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map { preferences ->
            preferences[PreferenceKeys.DARK_MODE] ?: false
        }

    val startDate: Flow<Long> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e("data store", "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map { preferences ->
            preferences[PreferenceKeys.START_DATE] ?: System.currentTimeMillis()
        }

    val hasSound: Flow<Boolean> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e("data store", "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map { preferences ->
            preferences[PreferenceKeys.SOUND] ?: true
        }

    val hustleDays: Flow<Int> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e("data store", "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map { preferences ->
            preferences[PreferenceKeys.HUSTLE_DAYS] ?: 0
        }

    val applications: Flow<Int> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e("data store", "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map { preferences ->
            preferences[PreferenceKeys.APPLICATIONS] ?: 0
        }

    suspend fun saveApplications() {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.APPLICATIONS] = preferences[PreferenceKeys.APPLICATIONS]?.plus(1) ?: 1
        }
    }

    suspend fun saveHustleDays() {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.HUSTLE_DAYS] = preferences[PreferenceKeys.HUSTLE_DAYS]?.plus(1) ?: 1
        }
    }

    suspend fun saveSound(sound: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.SOUND] = sound
        }
    }

    suspend fun saveDarkMode(darkMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.DARK_MODE] = darkMode
        }
    }

    suspend fun saveStartDate(startDate: Long) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.START_DATE] = startDate
        }
    }
}