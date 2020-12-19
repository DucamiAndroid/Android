package com.example.ducamiandroid.data.weather

import com.example.ducamiandroid.data.weather.Current
import com.example.ducamiandroid.data.weather.Daily

data class Weather(
    val current: Current,
    val daily: List<Daily>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int
) {
    override fun toString(): String {
        return "현재 온도 : ${current.temp}℃ | 체감 온도 : ${current.feels_like}℃"
    }
}