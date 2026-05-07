package com.example.mp0801

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mp0801.databinding.ActivityMainBinding
import com.example.mp0801.ui.theme.Mp2601Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener {
            binding.textView.run {
                if(text == "Hello") {
                    setText(R.string.World)
                    setTextColor(Color.Black.toArgb())
                    setBackgroundColor(Color.Cyan.toArgb())
                } else {
                    setText(R.string.Hello)
                    setTextColor(Color.Red.toArgb())
                    setBackgroundColor(Color.Yellow.toArgb())
                }
            }
        }

        setContent {
            Mp2601Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val modifier = Modifier.padding(innerPadding).background(Color.White)
                    Surface(modifier = modifier) {
//                        var name by remember { mutableStateOf("") }
//                        GreetingStateless(name, {name = it})
//                        GreetingStateful()
//                        Greeting(
//                            name = "Android",
//                            modifier = Modifier.padding(innerPadding)
//                        )
//                        RememberSaveableTest(modifier = Modifier.padding(innerPadding))
//                        MyAppLanguage(modifier = Modifier.padding(innerPadding))
//                        MainScreen(modifier = modifier)
//                        TestMainLazy()
//                        MyAppNav()
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var textState by remember { mutableStateOf("Hello") }
    var textColorState by remember { mutableStateOf(Color.Red) }
    var textBackgroundColorState by remember { mutableStateOf(Color.Yellow) }
    Column {
        Text(
            textState,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = textColorState,
            textAlign = TextAlign.Center,
            modifier = modifier
                .fillMaxWidth()
                .weight(1f)
                .background(textBackgroundColorState)
                .wrapContentHeight(align = Alignment.CenterVertically)
        )
        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            val isHello = textState == "Hello"
            textState = if (isHello) "World" else "Hello"
            textColorState = if (isHello) Color.Black else Color.Red
            textBackgroundColorState = if (isHello) Color.Cyan else Color.Yellow
        }) {
            Text("toggle")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Mp2601Theme {
        Greeting("Android")
    }
}