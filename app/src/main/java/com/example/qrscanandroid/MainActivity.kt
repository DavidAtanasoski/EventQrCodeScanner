package com.example.qrscanandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.example.qrscanandroid.ui.qrcode_scanner.ScannerScreen
import com.example.qrscanandroid.ui.theme.QrScanAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            QrScanAndroidTheme {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    ScannerScreen()
                }
            }
        }
    }
}
