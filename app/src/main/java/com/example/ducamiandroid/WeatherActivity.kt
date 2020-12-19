package com.example.ducamiandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ducamiandroid.service.RetrofitClient
import com.example.ducamiandroid.service.WeatherService
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class WeatherActivity : AppCompatActivity() {
    private val lat = 35.6632929
    private val lon = 128.4135717
    lateinit var retrofit: Retrofit
    lateinit var weatherService : WeatherService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        retrofit = RetrofitClient.getRetrofitInstance()
        weatherService = RetrofitClient.getWeatherService(retrofit)
        CoroutineScope(Dispatchers.IO).launch {
            val response = weatherService.getCurrentWeather(lat, lon, BuildConfig.API_KEY)
            if(response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    response.body()?.let {
                        textView.text = it.current.feels_like.toString()
                    }
                }
            }
        }
    }
}