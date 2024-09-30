package com.example.multiverseapp.presentation.mainScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.multiverseapp.R
import com.example.multiverseapp.ui.theme.MultiverseAppTheme

@Composable
fun PermissionRationaleDialog(
    requestPermission: () -> Unit,
    hidePermissionRationale: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier.shadow(2.dp, shape = RoundedCornerShape(20.dp), clip = true),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            Modifier
                .background(color = Color.White, shape = RoundedCornerShape(20.dp))
                .padding(15.dp)
                .fillMaxWidth(0.9f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = stringResource(R.string.permission_rationale_message),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
            }
            Row(
                Modifier
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Button(
                    onClick = { hidePermissionRationale() },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    )
                ) {
                    Text(text = "Cancel", color = Color.Black, fontSize = 18.sp)
                }
                Button(
                    onClick = { requestPermission() },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    )
                ) {
                    Text(text = "Ok", color = Color.Black, fontSize = 18.sp)
                }
            }
        }

    }


}

@Preview
@Composable
fun PermissionRationaleDialogPreview() {
    MultiverseAppTheme {
        Column(
            Modifier
                .fillMaxSize()
                .background(color = Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            PermissionRationaleDialog(requestPermission = { }, hidePermissionRationale = {})
        }
    }
}