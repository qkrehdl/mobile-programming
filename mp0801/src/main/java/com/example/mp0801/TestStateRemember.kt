package com.example.mp0801

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun GreetingStateless(name: String, onNameChange: (String) -> Unit) {
    TextField(
        value = name,
        onValueChange = onNameChange,
        label = { Text("Your name") }
    )
}

@Composable
fun GreetingStateful() {
    var name by remember { mutableStateOf("") }
    TextField(
        value = name,
        onValueChange = { name = it },
        label = { Text("Your name") }
    )
}

@Composable
fun RememberSaveableTest(modifier: Modifier = Modifier) {
    var aData by remember { mutableIntStateOf(0) }
    var bData by rememberSaveable { mutableIntStateOf(0) }
    Column {
        Row {
            Text("remember count : $aData", fontSize = 30.sp)
            Button(onClick = { aData++ }) { Text(text = "increment") }
        }
        Spacer(modifier = modifier.height(10.dp))
        Row {
            Text("remember count : $bData", fontSize = 30.sp)
            Button(onClick = { bData++ }) { Text(text = "increment") }
        }
    }
}

