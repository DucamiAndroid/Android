package com.example.ducamiandroid.service

import com.example.ducamiandroid.data.food.FoodBase
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodService {
    @GET("mealServiceDietInfo")
    fun getFood(
        @Query("KEY", encoded = true) KEY : String, //API키
        @Query("Type", encoded = true) Type : String, //데이터 타입
        @Query("pIndex", encoded = true) pIndex : String, //인덱스 크기
        @Query("pSize", encoded = true) pSize: String, //사이즈
        @Query("ATPT_OFCDC_SC_CODE", encoded = true) ATPT_OFCDC_SC_CODE : String, //교육청코드
        @Query("SD_SCHUL_CODE", encoded = true) SD_SCHUL_CODE : String, //학교코드
        @Query("MLSV_YMD", encoded = true) MLSV_YMD : String //원하는 날짜
    ) : Call<FoodBase>
}