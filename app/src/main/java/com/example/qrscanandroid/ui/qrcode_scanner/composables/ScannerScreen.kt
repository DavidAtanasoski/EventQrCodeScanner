package com.example.qrscanandroid.ui.qrcode_scanner.composables

import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.qrscanandroid.ui.qrcode_scanner.QrCodeScannerUiState

@Composable
fun ScannerScreen(
    uiState: QrCodeScannerUiState,
    navController: NavController,
    onQrCodeScanned: (String) -> Unit,
) {

    val context = LocalContext.current
    val lifeCycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }

    var hasCamPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasCamPermission = granted
        }
    )

    LaunchedEffect(key1 = true) {
        launcher.launch(android.Manifest.permission.CAMERA)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if (hasCamPermission) {
            CameraScreen(
                modifier = Modifier.weight(2f),
                cameraProviderFuture = cameraProviderFuture,
                lifeCycleOwner = lifeCycleOwner,
                uiState = uiState,
                navController = navController,
                onQrCodeScanned = onQrCodeScanned,
            )
        } else {
            Text(text = "Camera permission is required to scan QR code")
        }
    }
}

