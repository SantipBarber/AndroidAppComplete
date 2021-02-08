package com.example.pac_pm_ilerna

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.widget.Toast

class MyService : Service() {
    private lateinit var mp : MediaPlayer
    //Nueva clase encargada de devolver el estado actual del servicio
    private val binder = MyBinder()

    inner class MyBinder:Binder(){
        fun getService(): MyService = this@MyService
    }

    override fun onCreate() {
        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show()
        super.onCreate()
    }


    override fun onDestroy() {
        Toast.makeText(this, "Parando el servicio y liberando recursos", Toast.LENGTH_SHORT).show()
        mp.stop()
        mp.release()
        super.onDestroy()
    }

    fun startMusic(){
        if(!this::mp.isInitialized){
            mp = MediaPlayer.create(applicationContext, R.raw.cancion)
        }
        if(!mp.isPlaying){
            mp.start()
        }
        Toast.makeText(this, "Iniciando la música", Toast.LENGTH_SHORT).show()

    }
    fun pauseMusic(){
        if (mp.isPlaying){
            Toast.makeText(this, "Parando la música", Toast.LENGTH_SHORT).show()
            mp.pause()
        }
    }
    fun sleepService(){
        //LifeCycleScope
        //Thread
        //Coroutines
        Thread{
            Toast.makeText(this, "Dormimos el hilo", Toast.LENGTH_SHORT).show()
            Thread.sleep(15000) //Dormimos el hilo creado
        }.start()

    }

    /*override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //Esta función lo que hace es lanzar los comandos que aquí incluyamos

        var extras = intent?.extras
        var action = extras?.get("action")
        println(action)
        *//**
         * If(intent.value == 0) iniciar música
         * If(intent.value == 1) detener música
         * If(intent.value == 2) bloquear música
         *//*
        when(action){
            0 -> startMusic()
            1-> pauseMusic()
            2-> sleepService()
        }
        return START_STICKY
    }*/

    override fun onBind(intent: Intent): IBinder {
        return binder
    }
}