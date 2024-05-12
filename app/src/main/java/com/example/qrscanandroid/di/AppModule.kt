package com.example.qrscanandroid.di

import com.example.qrscanandroid.data.remote.EventApi
import com.example.qrscanandroid.data.repository.QrCodeRepositoryImpl
import com.example.qrscanandroid.domain.repository.QrCodeRepository
import com.example.qrscanandroid.utils.certificates.CertsManager.okHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideEventApi(): EventApi {
        return Retrofit.Builder()
            .baseUrl("https://ticketgen.eu-central-1.elasticbeanstalk.com/") // https://192.168.1.10:8443
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(EventApi::class.java)
    }

    @Provides
    @Singleton
    fun provideQrCodeRepository(eventApi: EventApi) : QrCodeRepository {
        return QrCodeRepositoryImpl(eventApi)
    }
}
