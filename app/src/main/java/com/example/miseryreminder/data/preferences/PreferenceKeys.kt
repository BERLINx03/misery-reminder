package com.example.miseryreminder.data.preferences

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey

object PreferenceKeys {
    const val PREFERENCE = "preference"
    val START_DATE = longPreferencesKey("start_date")
    val DARK_MODE = booleanPreferencesKey("dark_mode")
    val SOUND = booleanPreferencesKey("sound")
    val HUSTLE_DAYS = intPreferencesKey("hustle_days")
    val APPLICATIONS = intPreferencesKey("applications")

}