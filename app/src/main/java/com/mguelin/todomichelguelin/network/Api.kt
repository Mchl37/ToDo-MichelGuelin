package com.mguelin.todomichelguelin.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object Api {
    private const val TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjo3MzMsImV4cCI6MTY4MzU2MjkxM30.qw58uwB3jECjDRBTnaIAXhIa6_iAI_oIp4XhofpCraQ"

    private val retrofit by lazy {
        // client HTTP
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $TOKEN")
                    .build()
                chain.proceed(newRequest)
            }
            .build()

        val jsonSerializer = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }


        Retrofit.Builder()
            .baseUrl("https://android-tasks-api.herokuapp.com/api/")
            .client(okHttpClient)
            .addConverterFactory(jsonSerializer.asConverterFactory("application/json".toMediaType()))
            .build()

    }

    val userWebService : UserWebService by lazy {
        retrofit.create(UserWebService::class.java)
    }
    val taskWebService : TaskWebService by lazy {
        retrofit.create(TaskWebService::class.java)
    }
}