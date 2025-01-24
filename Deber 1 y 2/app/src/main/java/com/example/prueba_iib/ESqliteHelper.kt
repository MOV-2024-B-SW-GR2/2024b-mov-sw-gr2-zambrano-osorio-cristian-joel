package com.example.prueba_iib


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ESqliteHelper(
    contexto: Context?
) : SQLiteOpenHelper(
    contexto,
    "moviles",
    null,
    1
) {

    override fun onCreate(db: SQLiteDatabase?) {
        val crearTablaSalaDeCine = """
        CREATE TABLE SALA(
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nombre_de_la_sala_de_cine VARCHAR(50),
            numero_de_asientos INTEGER,
            habilitada INTEGER,
            horario_apertura VARCHAR(50)
        );
    """.trimIndent()  // Limpia la indentación innecesaria
        db?.execSQL(crearTablaSalaDeCine)

        val crearTablaAsiento = """
        CREATE TABLE ASIENTO(
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            ocupado INTEGER,
            tipo VARCHAR(50),
            precio REAL,
            sala_de_cine_id INTEGER,
            FOREIGN KEY(sala_de_cine_id) REFERENCES SALA(id) ON DELETE CASCADE
        );
    """.trimIndent()
        db?.execSQL(crearTablaAsiento)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
    override fun onConfigure(db: SQLiteDatabase?) {
    }


}
