package com.anurag.newapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val lat= intent.getStringExtra("lat")
        val long= intent.getStringExtra("long")
//        Toast.makeText(this,lat+" "+long,Toast.LENGTH_SHORT).show()
        getJosonData(lat,long)
    }


    private fun getJosonData(lat:String?,long:String?)
    {
        val API_KEY="6359baf0a80bd3d60e79ddca7f2c4e2b"
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${long}&appid=${API_KEY}"
        val jsonRequest = JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener { response ->
                    setValues(response)

        }, Response.ErrorListener { Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show()})

        queue.add(jsonRequest)

    }

    private fun setValues(response: JSONObject?) {

        if (response != null) {
            city.text=response.getString("name")

            var tempr=response.getJSONObject("main").getString("temp")
            tempr=((((tempr).toFloat()-273.15)).toInt()).toString()     // here we are convert temp cellcies to degree
            temp.text="${tempr}°C"

            var mintemp=response.getJSONObject("main").getString("temp_min")
            mintemp=((((mintemp).toFloat()-273.15)).toInt()).toString()
            min_temp.text="L${mintemp}°C"

            var maxtemp=response.getJSONObject("main").getString("temp_max")
            maxtemp=((ceil((maxtemp).toFloat()-273.15)).toInt()).toString()
            max_temp.text="H${maxtemp}°C"


            wind.text=response.getJSONObject("wind").getString("speed")
            humidity.text=response.getJSONObject("main").getString("humidity")+"%"
            degree.text="Degree:"+response.getJSONObject("wind").getString("deg")+"°"
            weather.text=response.getJSONArray("weather").getJSONObject(0).getString("main")


        }

    }
}