package com.example.mp0802

import android.app.Application
import com.example.mp0802.retrofit.NetworkService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }

    companion object {
        const val QUERY = "chatGPT"
        const val API_KEY = "c9cafc9f3de54647b16e92ab8a513baa"
        const val BASE_URL = "https://newsapi.org"
        const val USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36"

        private val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("User-Agent", USER_AGENT)
                    .build()
                chain.proceed(request)
            }
            .build()

        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val networkService: NetworkService = retrofit.create(NetworkService::class.java)
    }
}