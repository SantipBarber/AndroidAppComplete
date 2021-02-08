package com.example.pac_pm_ilerna

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.ImageView
import android.widget.Switch
import android.widget.Toast

class Activity3 : AppCompatActivity() {
    private lateinit var myService: MyService
    //Nos indicará si el servicio se ha enlazado
    private var bound: Boolean = false

    //Creamos la conexión
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            bound = true
            myService = (service as MyService.MyBinder).getService()
            Toast.makeText(applicationContext, "Enlazando el servicio", Toast.LENGTH_SHORT)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            bound = false
            Toast.makeText(applicationContext, "Deshabilitando enlace", Toast.LENGTH_SHORT)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_3)
        //De entrada, los botones estarán desactivados
        enableElement(false)

        /****************************************/
        //Código necesario para el botón de VOLVER
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Activity 2 MyDatabase"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        /****************************************/

        //Botones
        val btnPlayService: View = findViewById<ImageView>(R.id.btnPlay)
        val btnStopService: View = findViewById<ImageView>(R.id.btnPause)
        val btnBlockService: View = findViewById<ImageView>(R.id.btnBlock)
        val btnStartStopService : Switch = findViewById<Switch>(R.id.switch1)

        //Intent común para arrancar o parar el servicio
        val intentStartStopService = Intent(this, MyService::class.java)

        btnStartStopService.setOnClickListener {
            if (btnStartStopService.isChecked) {
                startService(intentStartStopService)
                bindService(intentStartStopService, connection, Context.BIND_AUTO_CREATE)
                //Toast.makeText(this, "Has activado el servicio", Toast.LENGTH_SHORT).show()
                //Activamos los botones para poder escuchar la música
                enableElement(true)
            } else {
                stopService(intentStartStopService)
                unbindService(connection)
                //Toast.makeText(this, "Servicio terminado", Toast.LENGTH_SHORT).show()
                //Desactivamos los botones
                enableElement(false)
            }
        }

        btnPlayService.setOnClickListener {
            /*val intentPlayMusic = Intent(this, MyService::class.java)
            intentPlayMusic.putExtra("action", 0)
            startService(intentPlayMusic)*/
            if (bound) myService.startMusic()
            //Toast.makeText(this, "Has activado la música", Toast.LENGTH_SHORT).show()
        }

        btnStopService.setOnClickListener {
            /*val intentPauseService = Intent(this, MyService::class.java)
            intentPauseService.putExtra("action", 1)
            startService(intentPauseService)*/

            if (bound) myService.pauseMusic()
            //Toast.makeText(this, "Has parado la música", Toast.LENGTH_SHORT).show()
        }

        btnBlockService.setOnClickListener {
            /*val intentBlockService = Intent(this, MyService::class.java)
            intentBlockService.putExtra("action", 2)
            startService(intentBlockService)*/

            if (bound) myService.sleepService()
            //Toast.makeText(this, "Has bloqueado el servicio", Toast.LENGTH_SHORT).show()
        }
    }

    fun enableElement(action: Boolean){
        findViewById<ImageView>(R.id.btnPlay).isEnabled = action
        findViewById<ImageView>(R.id.btnPause).isEnabled = action
        findViewById<ImageView>(R.id.btnBlock).isEnabled = action
    }

    override fun onSupportNavigateUp(): Boolean {
        Toast.makeText(this, "<-- A la activity Main", Toast.LENGTH_SHORT).show()
        onBackPressed()
        return true
    }
}