package com.example.mp0701_ANR

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mp0701_ANR.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

// ToDo : CoroutineScope 선언
val defaultScope = CoroutineScope(Dispatchers.Default)
val ioScope = CoroutineScope(Dispatchers.IO)
val mainScope = CoroutineScope(Dispatchers.Main)

class MainActivity : AppCompatActivity() {
    // ToDo : Channel 선언 - 전송할 데이터 타입 설정
    private val channel = Channel<Pair<Int, ULong>>()
    private lateinit var binding: ActivityMainBinding

    private fun printResult(idx:Int, sum: ULong, isEnable: Boolean) {
        val btnText = if (!isEnable) "계산중..." else "계산"
        when(idx) {
            1 -> {
                binding.textView1.text = sum.toString()
                binding.calcButton1.isEnabled = isEnable
                binding.calcButton1.text = btnText
            }
            else -> {
                binding.textView2.text = sum.toString()
                binding.calcButton2.isEnabled = isEnable
                binding.calcButton2.text = btnText
            }
        }
    }

    private fun sum(to: Long): ULong {
        var result = 0UL
        val time = measureTimeMillis {
            for (i in 1L..to) {
                result += i.toULong()
            }
        }
        Log.d("mp0701_ANR", "Sum computation took : $time ms")
        return result
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val v1 = 5_000_000_000
        val v2 = 4_000_000_000
        binding.textView1.text = "sum(" + v1 + ")"
        binding.textView2.text = "sum(" + v2 + ")"

        // ToDo : Main Thread 에 sum 결과값 출력을 위한 Coroutine launch
        mainScope.launch {
            channel.consumeEach { (idx, sum) ->
                printResult(idx, sum, true)
            }
        }

        binding.calcButton1.setOnClickListener {
            printResult(1, 0UL, false)
            // ToDo : sum 결과값 계산을 위한 CPU 작업용 Thread에 Coroutine launch
            defaultScope.launch {
                val sum = sum(v1)
                channel.send(Pair(1, sum))
            }
        }

        binding.calcButton2.setOnClickListener {
            printResult(2, 0UL, false)
            // ToDo : sum 결과값 계산을 위한 CPU 작업용 Thread에 Coroutine launch
            defaultScope.launch {
                val sum = sum(v2)
                channel.send(Pair(2, sum))
            }
        }
    }
}