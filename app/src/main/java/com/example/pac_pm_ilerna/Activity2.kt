package com.example.pac_pm_ilerna

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Activity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)
        /****************************************/
        //Código necesario para el botón de VOLVER
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Activity 2 MyDatabase"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        /****************************************/

        //Según el botón pulsado se lanzará el Activity correspondiente para realizar la acción.

        findViewById<Button>(R.id.btnCreate).setOnClickListener {
            val intentToCreateTable = Intent(this, CreateTableActivity::class.java)
            startActivity(intentToCreateTable)
            Toast.makeText(applicationContext, "--> Create Table Activity", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.btnInsert).setOnClickListener {
            val intentToinsertData = Intent(this, InsertDataActivity::class.java)
            startActivity(intentToinsertData)
            Toast.makeText(applicationContext, "--> Insert Data Activity", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.btnQuery).setOnClickListener {
            val intentToQueryActivity = Intent(this, DataQueryActivity::class.java)
            startActivity(intentToQueryActivity)
            Toast.makeText(applicationContext, "--> Query Data Activity", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        Toast.makeText(this, "<-- A la activity Main", Toast.LENGTH_SHORT).show()
        onBackPressed()
        return true
    }
}
