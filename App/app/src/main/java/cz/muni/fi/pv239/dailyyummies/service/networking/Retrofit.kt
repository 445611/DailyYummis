package cz.muni.fi.pv239.dailyyummies.service.networking

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retrofit(context: Context) {

    private val apiUrl = "https://developers.zomato.com/api/v2.1/"

    val client = OkHttpClient.Builder()
        //.addNetworkInterceptor(StethoInterceptor())
        //.addNetworkInterceptor(ChuckInterceptor(context))
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(apiUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val myApiJson = retrofit.create(ZomatoApi::class.java)

}