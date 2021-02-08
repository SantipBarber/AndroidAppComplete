package com.example.pac_pm_ilerna

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    //Declaración inicial de los botones: ACTIVITY2 Y ACTIVITY3
    private lateinit var btnToActivity2: Button
    private lateinit var btnToActivity3: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Asignación de los botones previamente declarados
        btnToActivity2 = findViewById(R.id.btnActivity2)
        btnToActivity3 = findViewById(R.id.btnActivity3)

        //Acciones que realizan los botones al ser "Clickados"

        btnToActivity2.setOnClickListener {
            startActivity(Intent(this, Activity2::class.java))
            Toast.makeText(this, "Activity 2 -->", Toast.LENGTH_SHORT).show()
        }

        btnToActivity3.setOnClickListener {
            startActivity(Intent(this, Activity3::class.java))
            Toast.makeText(this, "Activity 3 -->", Toast.LENGTH_SHORT).show()
        }


    }
}