package com.example.qrscanandroid.ui.qrcode_scanner.composables

import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.example.qrscanandroid.ui.qrcode_scanner.QrCodeScannerUiState
import com.example.qrscanandroid.utils.scanner.QrCodeAnalyzer
import com.google.common.util.concurrent.ListenableFuture

@Composable
fun CameraScreen(
    modifier: Modifier = Modifier,
    cameraProviderFuture: ListenableFuture<ProcessCameraProvider>,
    lifeCycleOwner: LifecycleOwner,
    uiState: QrCodeScannerUiState,
    navController: NavController,
    onQrCodeScanned: (String) -> Unit,
) {
    var code by remember { mutableStateOf("") }
    var isRequestInProgress by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.isSuccess, uiState.isFailed) {
        if (uiState.isSuccess || uiState.isFailed) {
            code = ""
            isRequestInProgress = false
            navController.navigate("scan_status_screen")
        }
    }

    DisposableEffect(key1 = lifeCycleOwner) {
        onDispose {
            // Reset the state when the screen is dismissed
            code = ""
            isRequestInProgress = false
        }
    }

    when {
        uiState.isLoading -> LoadingScreen()
        else -> {
            AndroidView(
                factory = { context ->
                    val previewView = PreviewView(context)
                    val preview = Preview.Builder().build()
                    val selector = CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build()

                    preview.setSurfaceProvider(previewView.surfaceProvider)
                    val imageAnalysis = ImageAnalysis.Builder()
                        .setTargetResolution(
                            Size(
                                previewView.width,
                                previewView.height
                            )
                        )
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()

                    imageAnalysis.setAnalyzer(
                        ContextCompat.getMainExecutor(context),
                        QrCodeAnalyzer { result ->
                            // Handle scanned QR code
                            code = result

                            if (!isRequestInProgress) {
                                isRequestInProgress = true
                                onQrCodeScanned(result)
                            }
                        }
                    )

                    try {
                        cameraProviderFuture.get().bindToLifecycle(
                            lifeCycleOwner,
                            selector,
                            preview,
                            imageAnalysis
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    previewView
                },
                modifier = modifier
            )

            Text(
                text = code,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
            )
        }
    }
}
