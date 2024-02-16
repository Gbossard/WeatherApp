package com.example.weatherapp.data

import android.util.Log
import com.example.weatherapp.model.WeatherCity
import com.example.weatherapp.network.WeatherApiService
import kotlinx.coroutines.delay

interface WeatherRepository {
    suspend fun getWeatherParis(): WeatherCity
    suspend fun getWeatherRennes(): WeatherCity
    suspend fun getWeatherNantes(): WeatherCity
    suspend fun getWeatherBordeaux(): WeatherCity
    suspend fun getWeatherLyon(): WeatherCity
    suspend fun getWeatherList(): List<WeatherCity>
}

class NetworkWeatherRepository(
    private val weatherApiService: WeatherApiService
) : WeatherRepository {
    override suspend fun getWeatherParis(): WeatherCity = weatherApiService.getWeatherParis()

    override suspend fun getWeatherRennes(): WeatherCity = weatherApiService.getWeatherRennes()

    override suspend fun getWeatherNantes(): WeatherCity = weatherApiService.getWeatherNantes()

    override suspend fun getWeatherBordeaux(): WeatherCity = weatherApiService.getWeatherBordeaux()

    override suspend fun getWeatherLyon(): WeatherCity = weatherApiService.getWeatherLyon()

    override suspend fun getWeatherList(): List<WeatherCity> {
        return listOf(
            getWeatherRennes(),
            getWeatherParis(),
            getWeatherNantes(),
            getWeatherBordeaux(),
            getWeatherLyon()
        )
    }
}