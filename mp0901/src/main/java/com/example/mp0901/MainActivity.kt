package com.example.mp0901

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.mp0901.ui.theme.Mp2601Theme

class MainActivity : ComponentActivity() {
    private val notesViewModel by viewModels<NoteViewModel>()
    private val settingsViewModel by viewModels<SettingsViewModel>{
        SettingsViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val uiState by settingsViewModel.uiState.collectAsState()
            val targetState = when (uiState.darkModeOption) {
                DarkModeOption.SYSTEM -> isSystemInDarkTheme()
                DarkModeOption.DARK -> true
                DarkModeOption.LIGHT -> false
            }
            Crossfade(targetState = targetState, label = "ThemeTransition") { isDarkTheme ->
                Mp2601Theme(darkTheme = isDarkTheme) {
                    MyAppNav(
                        noteViewModel = notesViewModel,
                        settingsViewModel = settingsViewModel
                    )
                }
            }
        }
        // testSharedPreferences()
    }

    private fun testSharedPreferences() {
        val prefs = getPreferences(Context.MODE_PRIVATE)
        prefs.edit().run {
            putString("data1", "hello")
            putInt("data2", 10)
            commit()
        }
        val data1 = prefs.getString("data1", "world")
        val data2 = prefs.getInt("data2", 20)
        val data3 = prefs.getBoolean("data3", false)
        Log.d("mp0901", "prefs: data1=${data1}, data2=${data2}, data3=${data3}")
        TestSharedPreferences(this)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Mp2601Theme {
    }
}