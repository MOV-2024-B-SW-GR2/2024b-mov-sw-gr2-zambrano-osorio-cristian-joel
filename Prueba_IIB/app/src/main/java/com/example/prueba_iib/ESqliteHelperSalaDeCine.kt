package com.example.prueba_iib

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ESqliteHelperSalaDeCine(context: Context) : SQLiteOpenHelper(context, "CineDB", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE SalaDeCine (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                numerosDeAsientos INTEGER NOT NULL,
                habilitada INTEGER NOT NULL,
                horarioApertura TEXT NOT NULL
            )
        """
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS SalaDeCine")
        onCreate(db)
    }

    fun crearSalaDeCine(nombre: String, numerosDeAsientos: Int, habilitada: Boolean, horarioApertura: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("nombre", nombre)
            put("numerosDeAsientos", numerosDeAsientos)
            put("habilitada", if (habilitada) 1 else 0)
            put("horarioApertura", horarioApertura)
        }
        val result = db.insert("SalaDeCine", null, values)
        db.close()
        return result != -1L
    }

    fun consultarSalaDeCinePorNombre(nombre: String): SalaDeCine? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM SalaDeCine WHERE nombre = ?", arrayOf(nombre))
        return if (cursor.moveToFirst()) {
            val sala = SalaDeCine(
                cursor.getString(1),
                cursor.getInt(2),
                cursor.getInt(3) == 1,
                cursor.getString(4)
            )
            cursor.close()
            sala
        } else {
            cursor.close()
            null
        }
    }

    fun actualizarSalaDeCinePorNombre(nombre: String, numerosDeAsientos: Int, habilitada: Boolean, horarioApertura: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("numerosDeAsientos", numerosDeAsientos)
            put("habilitada", if (habilitada) 1 else 0)
            put("horarioApertura", horarioApertura)
        }
        val result = db.update("SalaDeCine", values, "nombre = ?", arrayOf(nombre))
        db.close()
        return result > 0
    }

    fun eliminarSalaDeCinePorNombre(nombre: String): Boolean {
        val db = writableDatabase
        val result = db.delete("SalaDeCine", "nombre = ?", arrayOf(nombre))
        db.close()
        return result > 0
    }
}
