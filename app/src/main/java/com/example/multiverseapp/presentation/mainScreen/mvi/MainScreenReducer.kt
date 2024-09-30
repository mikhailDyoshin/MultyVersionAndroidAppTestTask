package com.example.multiverseapp.presentation.mainScreen.mvi

import com.example.multiverseapp.domain.LocationService

class MainScreenReducer(private val locationService: LocationService) {

    suspend fun reduce(intent: MainScreenIntent): MainScreenState {
        return when (intent) {
            MainScreenIntent.FetchUserLocation -> {
                val location = locationService.getUserLocation()
                location.fold(
                    onSuccess = { locationData ->
                        MainScreenState(locationData)
                    },
                    onFailure = { e ->
                        MainScreenState(locationData = null, errorMessage = e.message)
                    }
                )

            }

            MainScreenIntent.DisplayPermissionRationale -> MainScreenState(
                displayPermissionRationale = true
            )

            MainScreenIntent.HidePermissionRationale -> MainScreenState(
                displayPermissionRationale = false
            )
        }
    }

}