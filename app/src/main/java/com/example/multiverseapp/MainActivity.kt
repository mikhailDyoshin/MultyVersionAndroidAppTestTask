package com.example.multiverseapp

import com.example.multiverseapp.domain.gms.GmsLocationService
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.multiverseapp.ui.theme.MultiverseAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val requestPermissionLauncher = requestPermissionLauncher(onGranted = {
            Toast.makeText(
                this,
                "Permission granted",
                Toast.LENGTH_SHORT
            ).show()
        }, onShowPermissionRationale = {
            Toast.makeText(
                this,
                "Allow the app to use the location to get user's coordinates.",
                Toast.LENGTH_LONG
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
            },
            onShowPermissionRationale = {
                // Do nothing
            }
        )

        enableEdgeToEdge()
        setContent {
            MultiverseAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        // This composable should have a 'state' parameter, that controls its UI
                        getLocation = {
                            // I can make the function below to interact with a view-model,
                            // to change its state that is applied to the UI
                            // This way I can show dialogs or
                            requestLocationPermission(
                                requestPermissionLauncher = requestPermissionLauncher,
                                onPermissionGranted = {
                                    // Here I call a method that gets user's location
                                    // and updated the view-model's state
                                    Toast.makeText(
                                        this,
                                        "Loading location",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                onShowPermissionRationale = {
                                    // Here I can display a dialog-window that says about
                                    // necessity to allow the app to use the device location
                                    requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
                                }
                            )
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

        val permission = android.Manifest.permission.ACCESS_FINE_LOCATION

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

@Composable
fun Greeting(getLocation: () -> Unit, modifier: Modifier = Modifier) {

    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    val name = stringResource(id = R.string.client_name)
    val color = colorResource(R.color.client_color)
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Hello! I'm $name",
            color = color,
            modifier = modifier,
            fontSize = 24.sp
        )

        Button(onClick = {
            getLocation()
            scope.launch {
                val location = GmsLocationService(context).getUserLocation()
                location
                    .onSuccess {
                        Log.d("MyLocation", location.toString())
                    }
                    .onFailure { e ->
                        Log.d("MyLocation", "Failed to load location: $e")
                    }
            }

        }, Modifier.padding(top = 20.dp)) {
            Text(text = "Get location", fontSize = 20.sp)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MultiverseAppTheme {
        Greeting(getLocation = {})
    }
}