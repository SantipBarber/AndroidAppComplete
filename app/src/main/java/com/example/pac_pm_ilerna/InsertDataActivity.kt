package com.example.pac_pm_ilerna

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import android.database.Cursor as Cursor1

class InsertDataActivity : AppCompatActivity(){
    private lateinit var tableSelect : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_data)

        /****************************************/
        // Código necesario para el botón de VOLVER
        // actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Activity 2 MyDatabase"
        // set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        /****************************************/

        // Variables necesarias para el INSERT
        val btnInsertData = findViewById<Button>(R.id.insertData)
        val idInsert = findViewById<EditText>(R.id.insertCol1)
        val nameInsert = findViewById<EditText>(R.id.insertCol2)
        val integerInsert = findViewById<EditText>(R.id.insertCol3)

        // Variables necesarias para el Spinner, con una consulta a la BBDD para construirlo
        val myTablesList = ArrayList<String>()
        val admindb = DatabaseManager(applicationContext, "MyDatabase", null, 1)
        val dbRead = admindb.readableDatabase
        val cursor : Cursor1 = dbRead.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null)


        if (cursor.moveToFirst()){
            myTablesList.add("Seleccione")
            while(cursor.moveToNext()){
                myTablesList.add(cursor.getString(0))
            }
        }
        for(i in myTablesList.indices){
            println(myTablesList[i])
        }
        // Vamos a rellenar el Spinner con las tablas obtenidas de la consulta anterior
        val comboTable : Spinner = findViewById(R.id.tableSpinner)
        val adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, myTablesList)
        //val adapter = ArrayAdapter.createFromResource(this, myTablesList.hashCode(), android.R.layout.simple_spinner_item)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        comboTable.adapter = adapter1

        // Recogemos el nombre de la tabla a la que vamos a insertar datos
        comboTable.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                tableSelect = comboTable.selectedItem.toString()
                println(tableSelect)
                /**
                 * Mi intención era seleccionar los nombres de las columnas de la tablas escogida
                 * y asignar ese nombre de columna a los input para que a la hora de realizaer el
                 * insert se almacenaran en la columna correcta. Pero no soy capaz.
                 */
                /*if (tableSelect != "Seleccione"){
                    println("Probando el if de la entrada al cursor")
                    val cursor2 : Cursor1 = dbRead.rawQuery("SELECT COLUMN_NAME FROM $tableSelect", null)
                    val myColumnList = ArrayList<String>()
                    if (cursor2.moveToFirst()){
                        while(cursor.moveToNext()){
                            myColumnList.add(cursor2.getString(0))
                        }
                    }
                    for(i in myColumnList.indices){
                        println(myColumnList[i])
                    }
                }
*/
                Toast.makeText(applicationContext, getString(comboTable.id), Toast.LENGTH_SHORT).show()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        dbRead.close()

        // Inserción de datos presentes en formulario
        btnInsertData.setOnClickListener {
            val admin = DatabaseManager(applicationContext, "MyDatabase", null, 1)
            val dbWrite = admin.writableDatabase

            val valuesInsert = ContentValues()
            Toast.makeText(this, "Has seleccionado Insertar Datos", Toast.LENGTH_SHORT).show()
            val builderInput = AlertDialog.Builder(this)
            builderInput.setTitle("Insertar Datos")
            builderInput.setMessage(
                    "Vas a insertar datos en la tabla $tableSelect ¿Quieres continuar?"
            )
            builderInput.setPositiveButton(android.R.string.ok) { _, _ ->
                Toast.makeText(
                        applicationContext,
                        "Inserción en $tableSelect",
                        Toast.LENGTH_SHORT
                ).show()

                /*valuesInsert.put("id$tableSelect", idInsert.text.toString())
                valuesInsert.put("name$tableSelect", nameInsert.text.toString())
                valuesInsert.put("value$tableSelect", integerInsert.text.toString())
                dbWrite.insert(tableSelect, null, valuesInsert)*/
                val sql = "INSERT INTO ${tableSelect} values(${idInsert.text.toString()}, '${nameInsert.text.toString()}', ${integerInsert.text.toString()})"
                dbWrite.execSQL(sql)
                //println(sqlQuery)
                //dbWrite.execSQL(sqlQuery)
                idInsert.setText("")
                nameInsert.setText("")
                integerInsert.setText("")
                //insertCol4.setText("")
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


