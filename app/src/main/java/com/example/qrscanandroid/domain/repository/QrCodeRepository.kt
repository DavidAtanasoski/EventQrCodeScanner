package com.example.qrscanandroid.domain.repository

interface QrCodeRepository {
    suspend fun doValidation(scannedId: String): Boolean
}