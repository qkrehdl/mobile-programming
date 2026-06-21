package com.example.mp0703_jukebox_service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import android.util.Log.e
import com.example.mp0703_jukebox_service.R
class MyJukeboxService : Service() {

    lateinit var player: MediaPlayer

    override fun onCreate() {
        super.onCreate()
        player = MediaPlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }

    override fun onBind(intent: Intent): IBinder {

        return object : MyJukeboxInterface.Stub() {

            override fun start(songId: Int) {
                Log.d("JUKEBOX", "start called: $songId")
                try {
                    if (::player.isInitialized) {
                        if (player.isPlaying) {
                            player.stop()
                        }
                        player.release()
                    }

                    val music = when (songId) {
                        0 -> R.raw.ih
                        1 -> R.raw.oh
                        2 -> R.raw.yb
                        else -> R.raw.ih
                    }

                    player = MediaPlayer.create(
                        this@MyJukeboxService,
                        music
                    )

                    player.start()

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun stop() {
                try {
                    if (player.isPlaying) {
                        player.stop()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun getMaxDuration(): Int {
                return try {
                    if (player.isPlaying) player.duration else 0
                } catch (e: Exception) {
                    0
                }
            }
        }
    }
}