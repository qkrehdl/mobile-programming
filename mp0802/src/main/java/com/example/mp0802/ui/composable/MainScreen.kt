package com.example.mp0802.ui.composable

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.mp0802.MyApplication
import com.example.mp0802.model.ItemModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun MainScreen(modifier: Modifier = Modifier){
    val datas = remember { mutableStateOf(listOf<ItemModel>()) }
    LaunchedEffect(Unit) {
        try {
            withContext(Dispatchers.IO) {
                val response = MyApplication.networkService.getList(
                    MyApplication.QUERY,
                    MyApplication.API_KEY,
                    1,
                    10
                )

                if (response.isSuccessful) {
                    datas.value = response.body()?.articles ?: listOf()
                } else {
                    Log.e("mp0802", "Response error: ${response.code()} ${response.message()}")
                }
            }
        } catch (e: Exception) {
            Log.e("mp0802", "Network request failed", e)
        }
    }

    // TODO:mp0802:LazyColumn 으로 뉴스 출력 하기
}