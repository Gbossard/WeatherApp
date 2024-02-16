package com.example.weatherapp.utils

import java.text.DecimalFormat

fun decimalFormat(number: Float): String {
    val df = DecimalFormat("#.#")
    return df.format(number)
}

fun getIcon(icon: String) : String {
    return "https://openweathermap.org/img/wn/${icon}.png"
}