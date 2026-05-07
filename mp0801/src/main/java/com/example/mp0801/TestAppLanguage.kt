package com.example.mp0801

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource


@Composable
fun MyAppLanguageBasic(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val greeting = stringResource(id = R.string.greeting)
    Text(text = greeting, modifier = modifier)
}

enum class AppLanguage { EN, KO }
data class Strings(
    val greeting: String,
    val farewell: String
)
val localizedStrings = mapOf(
    AppLanguage.EN to Strings("Hello", "Goodbye"),
    AppLanguage.KO to Strings("안녕하세요", "안녕히 가세요")
)
val LocalStrings = staticCompositionLocalOf<Strings>{
    error("No Strings Provided")
}
@Composable
fun HomeScreen() {
    val strings = LocalStrings.current
    Column {
        Text(text = strings.greeting)
        Text(text = strings.farewell)
    }
}
@Composable
fun LanguageSwitcher(currentLang: AppLanguage, onChange: (AppLanguage) -> Unit) {
    Row {
        Button(onClick = { onChange(AppLanguage.KO) }) {
            Text("한국어")
        }
        Button(onClick = { onChange(AppLanguage.EN) }) {
            Text("English")
        }
    }
}
@Composable
fun MyAppLanguage(modifier: Modifier = Modifier) {
    var lang by remember { mutableStateOf(AppLanguage.KO) }
    val strings = remember(lang) { localizedStrings[lang] ?: localizedStrings[AppLanguage.EN]!! }

    Column {
        LanguageSwitcher(currentLang = lang, onChange = { lang = it })
        CompositionLocalProvider(LocalStrings provides strings) {
            HomeScreen()
        }
    }
}
