package com.example.qrscanandroid.utils.certificates

import android.annotation.SuppressLint
import okhttp3.OkHttpClient
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object CertsManager {
    private fun createUnsafeSSLSocketFactory(): SSLSocketFactory {
        try {
            val trustAllCerts = arrayOf<TrustManager>(TrustAllCerts())
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            return sslContext.socketFactory
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    val okHttpClient = OkHttpClient.Builder()
        .hostnameVerifier { _, _ -> true }
        .sslSocketFactory(createUnsafeSSLSocketFactory(), TrustAllCerts())
        .build()
}

@SuppressLint("CustomX509TrustManager")
private class TrustAllCerts : X509TrustManager {
    @SuppressLint("TrustAllX509TrustManager")
    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
    }

    @SuppressLint("TrustAllX509TrustManager")
    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
    }

    override fun getAcceptedIssuers(): Array<X509Certificate> = emptyArray()
}