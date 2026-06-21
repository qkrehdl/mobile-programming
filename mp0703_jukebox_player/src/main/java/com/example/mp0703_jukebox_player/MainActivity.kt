package com.example.mp0703_jukebox_player

import android.R
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat.enableEdgeToEdge
import androidx.core.view.WindowInsetsCompat
import com.example.mp0703_jukebox_player.databinding.ActivityMainBinding
import com.example.mp0703_jukebox_service.MyJukeboxInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.*
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    var jukeboxService: MyJukeboxInterface? = null
    var jukeboxJob: Job? = null

    private val mainScope = CoroutineScope(Dispatchers.Main + Job())

    val songs = arrayOf("ih.mp3", "oh.mp3", "yb.mp3")
    var selectedSong = 0

    val jukeboxConnection: ServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            android.util.Log.d("JUKEBOX_PLAYER", "service connected")
            jukeboxService = MyJukeboxInterface.Stub.asInterface(service)

            // 서비스 연결되면 바로 재생
            jukeboxService?.start(selectedSong)

            changeView("play")

            val max = jukeboxService?.getMaxDuration() ?: 0
            binding.jukeboxProgress.max = max

            jukeboxJob = mainScope.launch {
                while (binding.jukeboxProgress.progress < binding.jukeboxProgress.max) {
                    delay(1000)
                    binding.jukeboxProgress.incrementProgressBy(1000)
                }
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            jukeboxService = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ---------------- LISTVIEW ----------------
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_single_choice,
            songs
        )

        binding.songList.adapter = adapter
        binding.songList.choiceMode = ListView.CHOICE_MODE_SINGLE

        binding.songList.setOnItemClickListener { _, _, position, _ ->
            selectedSong = position
        }

        // ---------------- PLAY ----------------
        binding.jukeboxPlay.setOnClickListener {
            val intent = Intent()
            intent.component = ComponentName(
                "com.example.mp0703_jukebox_service",
                "com.example.mp0703_jukebox_service.MyJukeboxService"
            )
            bindService(intent, jukeboxConnection, Context.BIND_AUTO_CREATE)
        }

        // ---------------- STOP ----------------
        binding.jukeboxStop.setOnClickListener {
            jukeboxStop()
        }
    }

    override fun onStop() {
        super.onStop()
        jukeboxStop()
    }

    private fun jukeboxStop() {

        jukeboxService?.stop()

        changeView("stop")

        try {
            unbindService(jukeboxConnection)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        jukeboxJob?.cancel()
    }

    fun changeView(mode: String) {

        when (mode) {

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
}