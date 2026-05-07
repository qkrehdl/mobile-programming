package com.example.mp0801

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TestRownColumn () {
    RowExample()
    ColumnExample()
}

@Composable
fun RowExample() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("왼쪽")
        Text("오른쪽")
    }
}

@Composable
fun ColumnExample() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color.Yellow),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("위")
        Text("가운데")
        Text("아래")
    }
}

@Composable
fun TestExample(modifier: Modifier = Modifier) {
    Column {
        Row(modifier = modifier.background(Color.Yellow).fillMaxWidth().height(100.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text ("A")
            Text ("B")
            Text ("C")
        }
        Row(modifier = modifier.background(Color.Yellow).fillMaxWidth().height(100.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text ("A")
            Text ("B")
            Text ("C")
        }
        Row(modifier = modifier.background(Color.Yellow).fillMaxWidth().height(100.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text ("A")
            Text ("B")
            Text ("C")
        }
    }
}