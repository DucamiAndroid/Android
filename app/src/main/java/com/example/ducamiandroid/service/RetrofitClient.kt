package com.example.ducamiandroid.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL : String = "http://api.openweathermap.org/data/2.5/"

    fun getRetrofitInstance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun getWeatherService(retrofit: Retrofit) : WeatherService {
        return retrofit.create(WeatherService::class.java)
    }
}