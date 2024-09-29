package com.example.multiverseapp.domain

interface LocationService {
    suspend fun getUserLocation(): Result<LocationDataModel>
}