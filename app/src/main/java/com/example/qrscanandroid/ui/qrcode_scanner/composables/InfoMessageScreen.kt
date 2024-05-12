package com.example.qrscanandroid.ui.qrcode_scanner.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.qrscanandroid.R
import com.example.qrscanandroid.ui.qrcode_scanner.QrCodeScannerUiState

@Composable
fun InfoMessageScreen(
    state: QrCodeScannerUiState,
    navigateBack: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier.wrapContentSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = if (state.isSuccess) {
                    Icons.Rounded.CheckCircle
                } else {
                    Icons.Rounded.Clear
                },
                contentDescription = null,
                modifier = Modifier
                    .width(210.dp)
                    .height(210.dp),
                tint = if (state.isSuccess) {
                    colorResource(id = R.color.green_checkmark)
                } else {
                    colorResource(id = R.color.red_error_sign)
                }
            )

            Text(
                text = if (state.isSuccess) {
                    "Successfully scanned!"
                } else {
                    "Something went wrong!"
                },
                modifier = Modifier.padding(top = 8.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                navigateBack()
            },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.black),
                contentColor = colorResource(id = R.color.white)
            )
        ) {
            Text(text = "Done", modifier = Modifier.padding(4.dp), fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.weight(0.1f))
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SuccessMessageScreenPreview() {
    InfoMessageScreen(
        state = QrCodeScannerUiState(
            isSuccess = false,
        ),
        navigateBack = {}
    )
}