package com.example.pac_pm_ilerna

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog

class DataQueryActivity : AppCompatActivity() {
    //Declaramos una variable para recoger la tabla escogida en el menú
    private lateinit var tableForQuery : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_query)

        /****************************************/
        // Código necesario para el botón de VOLVER
        // actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Activity 2 MyDatabase"
        // set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        /****************************************/

        //Variables necesarias para realizar la consulta en BBDD
        val myTableForQuery = ArrayList<String>()
        val admin = DatabaseManager(this, "MyDatabase", null, 1)
        val dbReadForQuery = admin.readableDatabase
        val cursorForQuery : Cursor = dbReadForQuery.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null)

        //Cargamos datos en el array para obtener todas las tablas
        if(cursorForQuery.moveToFirst()){
            myTableForQuery.add("Seleccione")
            while(cursorForQuery.moveToNext()){
                myTableForQuery.add(cursorForQuery.getString(0))
            }
        }
        //Comprobamos por consola que se nos cargan en el ArrayList
        for(i in myTableForQuery.indices){
            println(myTableForQuery[i])
        }

        //Ahora rellenamos el Spinner para que nos muestre las tablas y podamos escoger
        val comboTableQuery : Spinner = findViewById(R.id.querySpinner2)
        val adapterQuery = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item ,myTableForQuery)
        adapterQuery.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        comboTableQuery.adapter = adapterQuery

        //Ahora lo configuramos para que podamos recoger la tabla escogida
        comboTableQuery.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                tableForQuery = comboTableQuery.selectedItem.toString()
                //Comprobamos que lo selecciona por consola
                println(tableForQuery)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) { }
        }

        //Ahora configuramos las acciones que van a realizarse al pulsar el botón de consultar
        findViewById<Button>(R.id.queryDataBtn).setOnClickListener {

            val idInputForQuery = findViewById<EditText>(R.id.inputIdQuery).text.toString()

            //Alert Dialog de control
            val builderQuery = AlertDialog.Builder(this)
            builderQuery.setTitle("Consultar Datos")
            builderQuery.setMessage("Vas a consultar los datos de la tabla $tableForQuery ¿Quieres continuar?")

            //Según la respuesta
            builderQuery.setPositiveButton(android.R.string.ok) { _,  _ ->
                Toast.makeText(this, "Realizar consulta OK", Toast.LENGTH_SHORT).show()
                val row = dbReadForQuery.rawQuery("SELECT * FROM ${tableForQuery} WHERE id = ${idInputForQuery}", null)

                //Rellenamos los datos en la salida de la pantalla
                if(row.moveToFirst()){
                    findViewById<EditText>(R.id.idConsultaResult).setText(row.getString(0))
                    findViewById<EditText>(R.id.nameConsultaResult).setText(row.getString(1))
                    findViewById<EditText>(R.id.valueConsultaResult).setText(row.getString(2))
                } else {
                    Toast.makeText(applicationContext, "No hay datos sobre esa tabla", Toast.LENGTH_SHORT).show()
                }
            }
            builderQuery.setNegativeButton(android.R.string.cancel) { _, _ ->
                Toast.makeText(this, "Se ha cancelado la consulta", Toast.LENGTH_SHORT).show()
            }
            builderQuery.create().show()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        Toast.makeText(this, "<-- A la activity 2", Toast.LENGTH_SHORT).show()
        onBackPressed()
        return true
    }
}