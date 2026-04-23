package com.example.mp0703_jukebox_service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

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
            override fun start() {
                if (player.isPlaying) return
                player = MediaPlayer.create(this@MyJukeboxService, R.raw.yb)
                try {
                    player.start()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            override fun stop() {
                if (player.isPlaying) player.stop()
            }
            override fun getMaxDuration(): Int {
                return if (player.isPlaying) player.duration else 0
            }
        }
    }
}