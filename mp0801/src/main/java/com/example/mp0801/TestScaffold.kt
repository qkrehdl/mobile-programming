package com.example.mp0801

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScaffoldScreen() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("My App") })
        },
        bottomBar = {
            BottomAppBar {
                Text("Bottom Bar", modifier = Modifier.padding(16.dp))
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { println("FAB clicked") }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
        content = { paddingValues ->
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)) {
                Text("Main Content Area")
            }
        }
    )
}


