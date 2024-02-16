package com.example.weatherapp.data

import com.example.weatherapp.network.WeatherApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val weatherRepository : WeatherRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://api.openweathermap.org/data/2.5/"
    private val iconUrl = "https://openweathermap.org/img/wn/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json{
            ignoreUnknownKeys = true
            isLenient = true
        }.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }

    override val weatherRepository: WeatherRepository by lazy {
        NetworkWeatherRepository(retrofitService)
    }
}