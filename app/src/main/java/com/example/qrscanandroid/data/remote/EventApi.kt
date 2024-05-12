package com.example.qrscanandroid.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface EventApi {

    @POST("/api/qrcode/validate")
    suspend fun doValidation(@Body id: String): Response<Boolean>
}
