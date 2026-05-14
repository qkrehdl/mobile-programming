package com.example.mp0901

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("설정", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(5.dp))

        SettingItemSwitch(
            title = "알림 허용",
            checked = uiState.value.isNotificationEnabled,
            onCheckedChange = { viewModel.toggleNotification() }
        )

        Spacer(modifier = Modifier.height(10.dp))

        DarkModeDropdown(
            selectedOption = uiState.value.darkModeOption,
            onOptionSelected = { viewModel.setDarkModeOption(it) }
        )

        Spacer(modifier = Modifier.height(15.dp))

        LanguageDropdown(
            selectedLanguage = uiState.value.selectedLanguage,
            onLanguageSelected = { viewModel.selectLanguage(it) }
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text("앱 버전: 1.0.0", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = { viewModel.logout() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("로그아웃")
        }
    }
}

@Composable
fun SettingItemSwitch(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {
        Text(title, modifier = Modifier.weight(1f))
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
fun LanguageDropdown(selectedLanguage: String, onLanguageSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val languages = listOf("한국어", "English", "日本語")

    Box {
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(selectedLanguage)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            languages.forEach { language ->
                DropdownMenuItem(
                    text = { Text(language) },
                    onClick = {
                        onLanguageSelected(language)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun DarkModeDropdown(selectedOption: DarkModeOption, onOptionSelected: (DarkModeOption) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val options = DarkModeOption.values()

    Box {
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(selectedOption.toDisplayName())
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.toDisplayName()) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

fun DarkModeOption.toDisplayName(): String {
    return when (this) {
        DarkModeOption.SYSTEM -> "테마 (시스템 설정)"
        DarkModeOption.LIGHT -> "라이트 모드"
        DarkModeOption.DARK -> "다크 모드"
    }
}
