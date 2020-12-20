package com.example.ducamiandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import com.example.ducamiandroid.data.food.FoodBase
import com.example.ducamiandroid.data.food.Row
import com.example.ducamiandroid.service.FoodClient
import kotlinx.android.synthetic.main.activity_food.*
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import javax.security.auth.callback.Callback

class FoodActivity : AppCompatActivity() {

    var currentTime = Calendar.getInstance().time
    var time = SimpleDateFormat("YYYYMMdd", Locale.KOREA).format(currentTime)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        getSchoolFood()
    }

    fun getSchoolFood(){
        FoodClient.retrofitService.getFood("e40fc13904d84da4a8d398649c324133", "JSON", "1", "100",
            "D10", "7240393", ""+ time
            ).enqueue(object : retrofit2.Callback<FoodBase> {
            override fun onResponse(call: Call<FoodBase>, response: Response<FoodBase>) {
                Log.d("Logd", "onResponse")
                var food = response.body()?.mealServiceDietInfo?.get(1)?.row
                Log.d("Logd", "$food")

                if(food.isNullOrEmpty()){
                    food1.text = "급식이없어요"
                    food2.text = "급식이없어요"
                    food3.text = "급식이없어요"

                    Log.d("Logd", "다없는날")
                }
                Log.d("Logd", response.body()?.mealServiceDietInfo?.get(0)?.head?.get(0)?.list_total_count.toString())

                if (response.body()?.mealServiceDietInfo?.get(0)?.head?.get(0)?.list_total_count.toString() == "1") {
                    Log.d("Logd", "아침만있음")
                        food1.text = response.body()?.mealServiceDietInfo?.get(1)?.row?.get(0)?.DDISH_NM
                            food1.text = Html.fromHtml(food1.text.replace("[0-9]".toRegex(), "").replace(".", ""))
                        food2.text = "급식이없어요"
                        food3.text = "급식이없어요"
                } else if(response.body()?.mealServiceDietInfo?.get(0)?.head?.get(0)?.list_total_count.toString() == "2"){
                    Log.d("Logd", "아침점심있음")
                        food1.text = response.body()?.mealServiceDietInfo?.get(1)?.row?.get(0)?.DDISH_NM
                            food1.text = Html.fromHtml(food1.text.replace("[0-9]".toRegex(), "").replace(".", ""))
                        food2.text = response.body()?.mealServiceDietInfo?.get(1)?.row?.get(1)?.DDISH_NM
                            food2.text = Html.fromHtml(food2.text.replace("[0-9]".toRegex(), "").replace(".", ""))
                        food3.text = "급식이없어요"
                } else {
                    Log.d("Logd", "다있는날")
                        food1.text = response.body()?.mealServiceDietInfo?.get(1)?.row?.get(0)?.DDISH_NM
                            food1.text = Html.fromHtml(food1.text.replace("[0-9]".toRegex(), "").replace(".", ""))
                        food2.text = response.body()?.mealServiceDietInfo?.get(1)?.row?.get(1)?.DDISH_NM
                            food2.text = Html.fromHtml(food2.text.replace("[0-9]".toRegex(), "").replace(".", ""))
                        food3.text = response.body()?.mealServiceDietInfo?.get(1)?.row?.get(2)?.DDISH_NM
                            food3.text = Html.fromHtml(food3.text.replace("[0-9]".toRegex(), "").replace(".", ""))
                }
            }

                override fun onFailure(call: Call<FoodBase>, t: Throwable) {
                    Log.d("Logd", t.message.toString())
                }

            })
    }
}