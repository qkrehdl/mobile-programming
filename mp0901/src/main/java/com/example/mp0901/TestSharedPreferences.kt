package com.example.mp0901

import android.content.Context
import android.util.Log

fun TestSharedPreferences(context: Context) {
    val prefs = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    prefs.edit().run {
        putString("data1", "hello")
        putInt("data2", 100)
        commit()
    }
    val data1 = prefs.getString("data1", "android")
    val data2 = prefs.getInt("data2", 200)
    val data3 = prefs.getBoolean("data3", false)
    Log.d("mp0901", "prefs: data1=${data1}, data2=${data2}, data3=${data3}")
}

