package com.example.mp0801

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable

@Composable
fun TestMainLazy() {
    TestLazy()
}

@Composable
fun TestLazy() {
    LazyColumn {
        val datas = listOf("One", "two", "three", "four")
        items(datas) { item ->
            MyText(data = "$item")
        }
        itemsIndexed(datas) { index, item ->
            MyText(data = "[$index] $item")
        }
    }
}