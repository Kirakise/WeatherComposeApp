package com.example.weathercomposeapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weathercomposeapp.data.WeatherModel
import com.example.weathercomposeapp.screens.MainScreen
import com.example.weathercomposeapp.screens.TabLayout
import com.example.weathercomposeapp.ui.theme.WeatherComposeAppTheme
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherComposeAppTheme {
                val daylist = remember {
                    mutableStateOf(listOf<WeatherModel>())
                }
                GetCityData("London", this, daylist)
                Image(painter = painterResource(id = R.drawable.wall_bg),
                    contentDescription = "im1",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds)
                Column{
                    MainScreen()
                    TabLayout(daylist)
                }

            }
        }
    }
}

const val API_KEY = "46e3bec14ad737b4e74d54722eccb7e4"

private fun GetCityData(city : String, context : Context,
                        daylist : MutableState<List<WeatherModel>>
                        ){
    val url ="http://api.openweathermap.org/geo/1.0/direct?" +
            "q=$city" +
            "&limit=1&" +
            "appid=$API_KEY"
    val q = Volley.newRequestQueue(context)
    val SReq = StringRequest(
        Request.Method.GET,
        url,
        {
            response ->
            val tmp = JSONArray(response)[0] as JSONObject
            GetTempData(tmp.getString("lat"), tmp.getString("lon"), context, daylist)
        },
        {
            Log.d("MyLog", "Volley Error: $it")
        }
    )
    q.add(SReq)
}

private fun GetTempData(lat : String, lon : String, context : Context,
    daylist : MutableState<List<WeatherModel>>
){
    val url = "https://api.openweathermap.org/data/3.0/onecall?" +
            "lat=$lat&" +
            "lon=$lon&" +
            "appid=$API_KEY"
    val q = Volley.newRequestQueue(context)
    val SReq = StringRequest(
        Request.Method.GET,
        url,
        {
                response ->
                val list = getWeatherByDays(response)
                daylist.value = list
        },
        {
            Log.d("MyLog", "Volley Error: $it")
        }
    )
    q.add(SReq)
}

private fun getWeatherByDays(response : String) : List<WeatherModel>{
    if (response.isEmpty()) return listOf()
    val list = ArrayList<WeatherModel>()
    val mainObj = JSONObject(response)
    val days = mainObj.getJSONArray("daily")
    for (i in 0 until days.length()) {
        val item = days[i] as JSONObject
        val temp = WeatherModel()
        temp.city = "London"
        val sdf = java.text.SimpleDateFormat("dd'T'HH:mm'Z'")
        val date = java.util.Date(item.getString("dt"))
        temp.time = sdf.format(date)
        temp.currentTemp = item.getJSONObject("temp").getString("day")
        temp.condition = item.getJSONObject("weather").getString("main")
        temp.icon = item.getJSONObject("weather").getString("icon")


        list.add(temp)
    }
    return list
}