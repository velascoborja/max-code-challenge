package com.touchsurgery.networking.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.touchsurgery.networking.BuildConfig
import com.touchsurgery.networking.api.DigitalSurgeryService
import com.touchsurgery.networking.serialization.InstantIso8601Serializer
import kotlinx.datetime.Instant
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

val moduleNetworking = module {

    single<Json> {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = true
            coerceInputValues = true
            serializersModule = SerializersModule {
                contextual(Instant::class, InstantIso8601Serializer)
            }
        }
    }

    single<OkHttpClient> {
        val builder = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BASIC)
            }
            builder.addInterceptor(loggingInterceptor)
            builder.addInterceptor(ChuckerInterceptor(androidContext()))
        }

        builder.build()
    }

    single<Retrofit> {
        val httpClient: OkHttpClient = get()

        val json: Json = get()

        Retrofit.Builder()
            .baseUrl("https://staging.touchsurgery.com/")
            .client(httpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    single<DigitalSurgeryService> {
        val retrofit: Retrofit = get()

        retrofit.create(DigitalSurgeryService::class.java)
    }
}
