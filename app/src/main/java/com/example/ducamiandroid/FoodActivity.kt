package com.example.ducamiandroid

import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.ducamiandroid.data.food.FoodBase
import com.example.ducamiandroid.service.FoodClient
import kotlinx.android.synthetic.main.activity_food.*
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class FoodActivity : AppCompatActivity() {

    private val currentTime = Calendar.getInstance().time
    private val time = SimpleDateFormat("YYYYMMdd", Locale.KOREA).format(currentTime)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)
        getSchoolFood()
    }

    private fun getSchoolFood() {
        FoodClient.retrofitService.getFood(BuildConfig.MEAL_KEY, "JSON", "1", "100",
                "D10", "7240393", time
        ).enqueue(object : retrofit2.Callback<FoodBase> {
            override fun onResponse(call: Call<FoodBase>, response: Response<FoodBase>) {
                food1.text = "급식이없어요"
                food2.text = "급식이없어요"
                food3.text = "급식이없어요"

                response.body()?.mealServiceDietInfo?.let {
                    val cnt = it[0].head?.get(0)?.list_total_count ?: return

                    it[1].row?.let { list ->
                        val regex = Regex("[0-9]+.")
                        if (cnt >= 1) {
                            food1.text = regex.replace(list[0].DDISH_NM ?: return, "")
                                        .replace("<br/>", "\n")
                        }
                        if (cnt >= 2) {
                            food2.text = regex.replace(list[1].DDISH_NM ?: return, "")
                                    .replace("<br/>", "\n")
                        }
                        if(cnt >= 3){
                            food3.text = regex.replace(list[2].DDISH_NM ?: return, "")
                                    .replace("<br/>", "\n")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<FoodBase>, t: Throwable) {
                Log.d("FOOD_ACTIVITY", t.message.toString())
            }

        })
    }
}