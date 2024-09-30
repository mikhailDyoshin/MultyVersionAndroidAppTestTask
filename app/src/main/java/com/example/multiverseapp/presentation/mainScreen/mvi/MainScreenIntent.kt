package com.example.multiverseapp.presentation.mainScreen.mvi

sealed class MainScreenIntent {
    data object FetchUserLocation : MainScreenIntent()
    data object DisplayPermissionRationale : MainScreenIntent()
    data object HidePermissionRationale : MainScreenIntent()
}