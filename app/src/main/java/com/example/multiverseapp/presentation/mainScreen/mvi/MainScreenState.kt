package com.example.multiverseapp.presentation.mainScreen.mvi

import com.example.multiverseapp.domain.LocationDataModel

data class MainScreenState(
    val locationData: LocationDataModel? = null,
    val errorMessage: String? = null,
    val displayPermissionRationale: Boolean = false
)
