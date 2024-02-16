package com.example.weatherapp.network

import com.example.weatherapp.model.WeatherCity
import retrofit2.http.GET

const val endUrl = "fr&units=metric&APPID=09d17e54203187db81a74a4be99594fe"

interface WeatherApiService {
    @GET("weather?q=Rennes,${endUrl}")
    suspend fun getWeatherRennes(): WeatherCity

    @GET("weather?q=Paris,${endUrl}")
    suspend fun getWeatherParis(): WeatherCity

    @GET("weather?q=Nantes,${endUrl}")
    suspend fun getWeatherNantes(): WeatherCity

    @GET("weather?q=Bordeaux,${endUrl}")
    suspend fun getWeatherBordeaux(): WeatherCity

    @GET("weather?q=Lyon,${endUrl}")
    suspend fun getWeatherLyon(): WeatherCity
}


