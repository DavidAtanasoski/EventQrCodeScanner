package com.example.qrscanandroid.data.repository

import android.util.Log
import com.example.qrscanandroid.data.remote.EventApi
import com.example.qrscanandroid.domain.repository.QrCodeRepository
import retrofit2.HttpException
import java.io.IOException

class QrCodeRepositoryImpl(
    private val api: EventApi
) : QrCodeRepository {

    private val TAG = "QrCodeRepositoryImpl"
    override suspend fun doValidation(scannedId: String): Boolean {

        try {
            val response = api.doValidation(scannedId)
            Log.d(TAG, scannedId)
            Log.d(TAG, "ResponseBefore ${response.body()}")
            return if (response.isSuccessful) {
                Log.d(TAG, "Response ${response.body()}")

                return response.body() == false
            } else {
                Log.d(TAG, "Error: ${response.code()}, ${response.message()}")
                false
            }
        } catch (e: IOException) {
            // Handle IO exception
            Log.d(TAG, "IOException: $e")
        } catch (e: HttpException) {
            // Handle HTTP exception
            Log.d(TAG, "HttpException: $e")
        }
        return false
    }
}