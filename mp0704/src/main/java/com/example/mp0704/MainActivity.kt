package com.example.mp0704

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.os.BatteryManager
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mp0704.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
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

        registerReceiver(
            batteryReceiver,
            IntentFilter(
                Intent.ACTION_BATTERY_CHANGED
            )
        )
    }
    private val batteryReceiver = object : BroadcastReceiver() {

        override fun onReceive(
            context: Context?,
            intent: Intent?
        ) {

            intent?.let {

                val status =
                    it.getIntExtra(
                        BatteryManager.EXTRA_STATUS,
                        -1
                    )

                val plugged =
                    it.getIntExtra(
                        BatteryManager.EXTRA_PLUGGED,
                        -1
                    )

                val (chargingText, imageResId) = when {

                    status == BatteryManager.BATTERY_STATUS_CHARGING &&
                            plugged == BatteryManager.BATTERY_PLUGGED_AC ->

                        "AC Plugged" to R.drawable.ac

                    status == BatteryManager.BATTERY_STATUS_CHARGING &&
                            plugged == BatteryManager.BATTERY_PLUGGED_USB ->

                        "USB Plugged" to R.drawable.usb

                    status == BatteryManager.BATTERY_STATUS_CHARGING ->

                        "Charging" to null

                    else ->

                        "Not Charging" to null
                }

                binding.chargingResultView.text =
                    chargingText

                imageResId?.let {

                    binding.chargingImageView
                        .setImageResource(it)

                } ?: binding.chargingImageView
                    .setImageBitmap(null)


                val level =
                    it.getIntExtra(
                        BatteryManager.EXTRA_LEVEL,
                        -1
                    )

                val scale =
                    it.getIntExtra(
                        BatteryManager.EXTRA_SCALE,
                        -1
                    )

                val batteryPct =
                    level / scale.toFloat() * 100

                binding.percentResultView.text =
                    String.format(
                        "Battery: %.1f %%",
                        batteryPct
                    )
            }

        }

    }
    override fun onDestroy() {

        super.onDestroy()

        unregisterReceiver(
            batteryReceiver
        )

    }
}