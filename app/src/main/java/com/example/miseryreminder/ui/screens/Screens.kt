package com.example.miseryreminder.ui.screens

sealed class Screens(val route: String) {
    object Home: Screens("home")
    object Applications: Screens("applications")
    object Settings: Screens("settings")
    object Help: Screens("help")
}