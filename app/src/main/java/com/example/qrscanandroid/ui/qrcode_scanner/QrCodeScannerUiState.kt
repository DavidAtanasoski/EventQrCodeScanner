package com.example.qrscanandroid.ui.qrcode_scanner

data class QrCodeScannerUiState(
    val scannedCode: String = "",

    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isFailed: Boolean = false,
)