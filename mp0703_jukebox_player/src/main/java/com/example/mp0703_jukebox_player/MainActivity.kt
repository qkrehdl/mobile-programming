package com.example.mp0703_jukebox_player

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mp0703_jukebox_player.databinding.ActivityMainBinding
import com.example.mp0703_jukebox_service.MyJukeboxInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val defaultScope = CoroutineScope(Dispatchers.Default)
val bgScope = CoroutineScope(Dispatchers.Default + Job())
val ioScope = CoroutineScope(Dispatchers.IO)
val mainScope = CoroutineScope(Dispatchers.Main)

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var jukeboxService: MyJukeboxInterface? = null
    var jukeboxJob: Job? = null

    val jukeboxConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            jukeboxService = MyJukeboxInterface.Stub.asInterface(service)
            jukeboxService?.run {
                start()
                changeView("play")
                binding.jukeboxProgress.max = this.maxDuration
                jukeboxJob = mainScope.launch {
                    while (binding.jukeboxProgress.progress < binding.jukeboxProgress.max) {
                        delay(1000)
                        binding.jukeboxProgress.incrementProgressBy(1000)
                    }
                }
                jukeboxJob!!.start()
            }
        }
        override fun onServiceDisconnected(name: ComponentName?) {
            jukeboxService = null
        }
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

        binding.jukeboxPlay.setOnClickListener {
            val intent = Intent("ACTION_JUKEBOX_SERVICE")
            intent.setPackage("com.example.mp0703_jukebox_service")
            bindService(intent, jukeboxConnection, Context.BIND_AUTO_CREATE)
        }

        binding.jukeboxStop.setOnClickListener {
            jukeboxStop()
        }
    }

    override fun onStop() {
        super.onStop()
        jukeboxStop()
    }
    private fun jukeboxStop() = jukeboxService?.run {
        stop()
        changeView("stop")
        unbindService(jukeboxConnection)
        jukeboxJob!!.cancel()
    }

    fun changeView(mode: String) = when(mode) {
        "play" -> {
            binding.jukeboxPlay.isEnabled = false
            binding.jukeboxStop.isEnabled = true
        }
        else -> {
            binding.jukeboxPlay.isEnabled = true
            binding.jukeboxStop.isEnabled = false
            binding.jukeboxProgress.progress = 0
        }
    }
}

