package com.example.weatherapp.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherInfo(
    val icon: String
)
