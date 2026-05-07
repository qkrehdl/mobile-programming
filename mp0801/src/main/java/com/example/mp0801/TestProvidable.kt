package com.example.mp0801

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


data class AppConfig(val isDarkMode: Boolean)

val LocalSwitchState = compositionLocalOf { false }
val LocalAppCounter = compositionLocalOf { 0 }
val LocalAppConfig = staticCompositionLocalOf { AppConfig(false) }

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var switchState by remember { mutableStateOf(true) }
    var appCounter by remember { mutableIntStateOf(0) }
    var appConfig = remember(switchState) { AppConfig(isDarkMode = switchState) }

    val onSwitchChange = { value: Boolean ->
        when(value) {
            true -> appCounter++
            else -> {}
        }
        switchState = value
    }

    val modifier2 = Modifier.width(100.dp).height(20.dp).background(Color.Yellow)
    Column {
        Row {
            MySwitch(switchState = switchState, onSwitchChange = onSwitchChange)
        }
        Spacer(modifier = modifier.then(modifier2))
        Row {
            CompositionLocalProvider(
                LocalSwitchState provides switchState,
                LocalAppCounter provides appCounter,
                LocalAppConfig provides appConfig
            ) {
                MySwitchText(switchState = switchState, modifier = modifier)
            }
        }
    }
}

@Composable
fun MySwitch(switchState: Boolean, onSwitchChange: (Boolean) -> Unit) {
    Switch(checked = switchState, onCheckedChange = onSwitchChange)
}

@Composable
fun MySwitchText(switchState: Boolean, modifier: Modifier = Modifier) {
    val localAppCounter = LocalAppCounter.current
    val localAppConfig = LocalAppConfig.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(if (localAppConfig.isDarkMode) Color.Gray else Color.White)
    ) {
        Row {
            Text(
                when (switchState) {
                    true -> "Switch On  "
                    else -> "Switch Off "
                } + ":$localAppCounter", fontSize = 40.sp
            )
        }
        Spacer(modifier = modifier.height(10.dp))
        Row {
            MySwitchText2()
        }
    }
}

@Composable
fun MySwitchText2() {
    val switchState = LocalSwitchState.current
    val localAppConfig = LocalAppConfig.current
    Text(when(switchState) {
        true -> "Switch On  "
        else -> "Switch Off "
    } + ":${localAppConfig.isDarkMode}", fontSize = 40.sp)
}
