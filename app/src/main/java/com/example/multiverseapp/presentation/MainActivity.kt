package com.example.multiverseapp.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.multiverseapp.R
import com.example.multiverseapp.domain.gms.GmsLocationService
import com.example.multiverseapp.presentation.mainScreen.MainScreen
import com.example.multiverseapp.presentation.mainScreen.mvi.MainScreenReducer
import com.example.multiverseapp.presentation.mainScreen.mvi.MainScreenViewModel
import com.example.multiverseapp.ui.theme.MultiverseAppTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gmsLocationService = GmsLocationService(this)
        val mainScreenReducer = MainScreenReducer(gmsLocationService)
        val viewModel = MainScreenViewModel(mainScreenReducer)

        val requestPermissionLauncher = requestPermissionLauncher(onGranted = {
            Toast.makeText(
                this,
                "Permission granted",
                Toast.LENGTH_SHORT
            ).show()
            viewModel.fetchLocation()
        }, onShowPermissionRationale = {
            Toast.makeText(
                this,
                this.getString(R.string.permission_rationale_message),
                Toast.LENGTH_SHORT
            ).show()
        })

        requestLocationPermission(
            requestPermissionLauncher = requestPermissionLauncher,
            onPermissionGranted = {
                Toast.makeText(
                    this,
                    "Loading location",
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.fetchLocation()
            },
            onShowPermissionRationale = {
                // Do nothing
            }
        )

        enableEdgeToEdge()
        setContent {
            MultiverseAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        state = viewModel.state.value,
                        getLocation = {
                            requestLocationPermission(
                                requestPermissionLauncher = requestPermissionLauncher,
                                onPermissionGranted = {
                                    viewModel.fetchLocation()
                                },
                                onShowPermissionRationale = {
                                    viewModel.displayPermissionRationale()
                                }
                            )
                        },
                        requestPermission = {
                            viewModel.hidePermissionRationale()
                            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                        },
                        hidePermissionRationale = {
                            viewModel.hidePermissionRationale()
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    /**
     * The function reacts on user's decision about a permission: was it granted or not.
     */
    private fun requestPermissionLauncher(
        onGranted: () -> Unit,
        onShowPermissionRationale: () -> Unit
    ): ActivityResultLauncher<String> {
        return registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                onGranted()
            } else {
                onShowPermissionRationale()
            }
        }
    }

    /**
     * The function checks if the location-permission was granted and then acts in 3 ways:
     * - It calls the [onPermissionGranted] if the location-permission was granted
     * - It calls the [onShowPermissionRationale] if the location-permission was denied previously
     * and the user tries to use a feature that requires the device's location information so a dialog-window
     * appears that says why to allow the permission is essential.
     * - In other cases the function requests the location-permission.
     */
    private fun requestLocationPermission(
        requestPermissionLauncher: ActivityResultLauncher<String>,
        onPermissionGranted: () -> Unit,
        onShowPermissionRationale: () -> Unit,
    ) {

        val permission = Manifest.permission.ACCESS_FINE_LOCATION

        when {
            ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                onPermissionGranted()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this, permission

            ) -> {
                onShowPermissionRationale()
            }

            else -> {
                requestPermissionLauncher.launch(
                    permission
                )
            }
        }
    }
}
