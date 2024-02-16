package com.example.weatherapp.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherCity(
    val id: Int,
    val name: String,
    val main: WeatherTemperature,
    val weather: List<WeatherInfo>
)

