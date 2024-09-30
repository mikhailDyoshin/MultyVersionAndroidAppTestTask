package com.example.multiverseapp.domain.gms

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.example.multiverseapp.domain.LocationDataModel
import com.example.multiverseapp.domain.LocationService
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.tasks.await

class GmsLocationService(private val context: Context) : LocationService {
    private val fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    override suspend fun getUserLocation(): Result<LocationDataModel> {
        try {
            if (!hasLocationPermission(context)) {
                return Result.failure(Exception("No permission to get location"))
            }

            val location = fusedLocationProviderClient.getCurrentLocation(
                Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                CancellationTokenSource().token,
            ).await()


            return if (location != null) {
                Result.success(LocationDataModel(location.latitude, location.longitude))
            } else {
                Result.failure(Exception("Location not available"))
            }


        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    private fun hasLocationPermission(context: Context): Boolean {
        return ActivityCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
}