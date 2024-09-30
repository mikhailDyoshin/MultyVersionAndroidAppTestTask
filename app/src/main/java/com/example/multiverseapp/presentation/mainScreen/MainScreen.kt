package com.example.multiverseapp.presentation.mainScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.multiverseapp.R
import com.example.multiverseapp.domain.LocationDataModel
import com.example.multiverseapp.presentation.mainScreen.components.PermissionRationaleDialog
import com.example.multiverseapp.presentation.mainScreen.mvi.MainScreenState
import com.example.multiverseapp.ui.theme.MultiverseAppTheme

@Composable
fun MainScreen(
    state: MainScreenState,
    getLocation: () -> Unit,
    requestPermission: () -> Unit,
    hidePermissionRationale: () -> Unit,
    modifier: Modifier = Modifier
) {
    val name = stringResource(id = R.string.client_name)
    val color = colorResource(R.color.client_color)
    Box(Modifier.fillMaxSize()) {
        Column(
            modifier.fillMaxWidth().fillMaxHeight(0.5f).align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = name,
                color = color,
                fontSize = 48.sp
            )
            Column(
                Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Long: ${state.locationData?.longitude}",
                    color = color,
                    fontSize = 24.sp,
                )
                Text(
                    text = "Lat: ${state.locationData?.latitude}",
                    color = color,
                    modifier = modifier,
                    fontSize = 24.sp
                )
            }

            Button(onClick = { getLocation() }) {
                Text(text = "Get location", fontSize = 20.sp)
            }
        }

        if (state.displayPermissionRationale) {
            PermissionRationaleDialog(
                requestPermission = { requestPermission() },
                hidePermissionRationale = { hidePermissionRationale() },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 80.dp)
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MultiverseAppTheme {
        MainScreen(
            state = MainScreenState(
                locationData = LocationDataModel(
                    longitude = 1.045,
                    latitude = 45.098
                ), displayPermissionRationale = true
            ),
            getLocation = {},
            requestPermission = {},
            hidePermissionRationale = {}
        )
    }
}