package com.example.mp0801

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TestBoxMain() {
    TestBoxEx()
}

@Composable
fun MyText(data: String, modifier: Modifier = Modifier) {
    Text(
        data,
        fontSize = 50.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .border(width = 4.dp, color = Color.Black)
            .padding(10.dp)
            .then(modifier)
    )
}

@Composable
fun TestBox() {
    // 기본 예제
    Box(
        modifier = Modifier
            .size(200.dp)
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Text("가운데 텍스트")
    }
    // 겹치기 예제
    Box(modifier = Modifier.size(120.dp)) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Red)
        )
        Box(
            modifier = Modifier
                .size(60.dp)
                .align(Alignment.BottomEnd)
                .background(Color.Blue)
        )
    }
    // 정렬 예제
    Box(modifier = Modifier.size(150.dp)) {
        Text("왼쪽 위", modifier = Modifier.align(Alignment.TopStart))
        Text("중앙", modifier = Modifier.align(Alignment.Center))
    }
}

@SuppressLint("MutableCollectionMutableState")
@Composable
fun TestBoxEx() {
    var aVisible by remember { mutableStateOf(mutableListOf(true, false)) }
    Column {
        Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
            this@Column.AnimatedVisibility(
                visible = aVisible.get(0), enter = fadeIn(), exit = fadeOut(),
                modifier = Modifier.align(Alignment.Center)
            ) {
                MyText(data = "A")
            }
            this@Column.AnimatedVisibility(
                visible = aVisible.get(1), enter = fadeIn(), exit = fadeOut(),
                modifier = Modifier.align(Alignment.Center)
            ) {
                MyText(data = "B")
            }
        }
        Row {
            Button(modifier = Modifier.weight(1f), onClick = {
                aVisible = mutableListOf(true, false)
            }) {
                Text("A")
            }
            Button(modifier = Modifier.weight(1f), onClick = {
                aVisible = mutableListOf(false, true)
            }) {
                Text("B")
            }
        }
    }
}
