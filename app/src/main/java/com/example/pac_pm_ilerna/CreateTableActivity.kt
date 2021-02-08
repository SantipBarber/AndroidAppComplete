package com.example.pac_pm_ilerna

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class CreateTableActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_table)
        /****************************************/
        //Código necesario para el botón de VOLVER
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Activity 2 MyDatabase"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        /****************************************/
        //Declaramos y asignamos el botón de CREAR y las entradas de texto
        val btnCrear = findViewById<Button>(R.id.createTableData)
        val inputNameTable = findViewById<EditText>(R.id.nameTable)
        val inputCol1 = findViewById<EditText>(R.id.col1)
        val inputCol2 = findViewById<EditText>(R.id.col2)
        val inputCol3 = findViewById<EditText>(R.id.col3)
        //var inputCol4 = findViewById<EditText>(R.id.col4)

        btnCrear.setOnClickListener {
            val admindb = DatabaseManager(applicationContext, "MyDatabase", null, 1)
            val dbWrite = admindb.writableDatabase
            val builderInput = AlertDialog.Builder(this)
            var nameTable = inputNameTable.text.toString()
            builderInput.setTitle("Crear Tabla")
            builderInput.setMessage(
                "Vas a crear la tabla $nameTable. Pero para que funcione los insert," +
                        " nombre de las columnas deben ser" +
                        " id$nameTable, name$nameTable y value$nameTable. ¿Quieres continuar?"
            )
            builderInput.setPositiveButton(android.R.string.ok) { _, _ ->
                Toast.makeText(
                    applicationContext,
                    "Se ha creado la tabla $nameTable",
                    Toast.LENGTH_SHORT
                ).show()
                val sqlQuery =
                    "CREATE TABLE IF NOT EXISTS ${inputNameTable.text.toString()} (${inputCol1.text.toString()} INTEGER, ${inputCol2.text.toString()}  TEXT, ${inputCol3.text.toString()} INTEGER)"
                println(sqlQuery)
                dbWrite.execSQL(sqlQuery)
                inputNameTable.setText("")
                inputCol1.setText("")
                inputCol2.setText("")
                inputCol3.setText("")
                //input.setText("")
                dbWrite.close()
            }
            builderInput.setNegativeButton(android.R.string.cancel) { _, _ ->
                Toast.makeText(
                    applicationContext,
                    "Se ha cancelado la creación de la tabla",
                    Toast.LENGTH_SHORT
                ).show()
            }
            builderInput.create().show()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        Toast.makeText(this, "<-- A la activity 2", Toast.LENGTH_SHORT).show()
        onBackPressed()
        return true
    }
}
