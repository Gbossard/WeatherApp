package com.example.weatherapp.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherTemperature(
    val temp: Float,
)

