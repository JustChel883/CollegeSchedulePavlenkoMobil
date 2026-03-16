package com.example.collegeschedulepavlenkomobil.data.network

import com.example.collegeschedulepavlenkomobil.data.api.ScheduleApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:5239/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val api: ScheduleApi = retrofit.create(ScheduleApi::class.java)
}