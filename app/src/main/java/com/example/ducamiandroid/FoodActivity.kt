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
                food1.text = "급식이없어요"       //foodText 다 초기화
                food2.text = "급식이없어요"
                food3.text = "급식이없어요"

                response.body()?.mealServiceDietInfo?.let { //mealServiceInfo가 null이 아니라면 let실행
                    val cnt = it[0].head?.get(0)?.list_total_count ?: return //cnt = list_total_count인데 list_total_count가 null이라면 return

                    it[1].row?.let { list -> // row가 null이 아니라면
                        val regex = Regex("[0-9]+.") //정규식 선언
                        if (cnt >= 1) { //list_total_count가 1보다 같거나 작다면
                            food1.text = regex.replace(list[0].DDISH_NM ?: return, "") //DDISH_NM에 regex가 선언해준 정규식에 걸리는게 있다면 그것을 없애준다
                                    .replace("<br/>", "\n")                           // <br/>을 \n로 바꾼다
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
                Log.d("Logd", t.message.toString())
            }
        })
    }
}