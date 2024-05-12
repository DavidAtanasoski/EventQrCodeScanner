package com.example.qrscanandroid

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.qrscanandroid.ui.qrcode_scanner.QrCodeScannerViewModel
import com.example.qrscanandroid.ui.qrcode_scanner.composables.InfoMessageScreen
import com.example.qrscanandroid.ui.qrcode_scanner.composables.ScannerScreen
import com.example.qrscanandroid.ui.theme.QrScanAndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            QrScanAndroidTheme {

                val viewModel = hiltViewModel<QrCodeScannerViewModel>()
                val navController = rememberNavController()

                val uiState by viewModel.uiState.collectAsState()

                NavHost(navController = navController, startDestination = "scanner_screen") {
                    composable("scanner_screen") {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            ScannerScreen(
                                uiState = uiState,
                                navController = navController,
                                onQrCodeScanned = viewModel::onQrCodeScanned,
                            )
                        }
                    }

                    composable("scan_status_screen") {
                        InfoMessageScreen(
                            state = uiState,
                            navigateBack = {
                                Log.d("scan_status_screen", "Pressed Back")
                                viewModel.resetState()
                                navController.navigate("scanner_screen") })
                    }
                }
            }
        }
    }
}
