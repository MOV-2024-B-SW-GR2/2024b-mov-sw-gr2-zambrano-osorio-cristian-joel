package com.example.prueba_iib

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ESqliteHelperAsiento(context: Context) : SQLiteOpenHelper(context, "CineDB", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE Asiento (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                numeroDeAsiento INTEGER NOT NULL,
                ocupado INTEGER NOT NULL,
                tipo TEXT NOT NULL,
                precio REAL NOT NULL,
                fechaReservacion TEXT
            )
        """
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Asiento")
        onCreate(db)
    }

    fun crearAsiento(numeroDeAsiento: Int, ocupado: Boolean, tipo: String, precio: Double, fechaReservacion: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("numeroDeAsiento", numeroDeAsiento)
            put("ocupado", if (ocupado) 1 else 0)
            put("tipo", tipo)
            put("precio", precio)
            put("fechaReservacion", fechaReservacion)
        }
        val result = db.insert("Asiento", null, values)
        db.close()
        return result != -1L
    }

    fun consultarAsientoPorNumero(numeroDeAsiento: Int): Asiento? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Asiento WHERE numeroDeAsiento = ?", arrayOf(numeroDeAsiento.toString()))
        return if (cursor.moveToFirst()) {
            val asiento = Asiento(
                cursor.getInt(1),
                cursor.getInt(2) == 1,
                cursor.getString(3),
                cursor.getDouble(4),
                cursor.getString(5)
            )
            cursor.close()
            asiento
        } else {
            cursor.close()
            null
        }
    }

    fun actualizarAsientoPorNumero(numeroDeAsiento: Int, ocupado: Boolean, tipo: String, precio: Double, fechaReservacion: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("ocupado", if (ocupado) 1 else 0)
            put("tipo", tipo)
            put("precio", precio)
            put("fechaReservacion", fechaReservacion)
        }
        val result = db.update("Asiento", values, "numeroDeAsiento = ?", arrayOf(numeroDeAsiento.toString()))
        db.close()
        return result > 0
    }

    fun eliminarAsientoPorNumero(numeroDeAsiento: Int): Boolean {
        val db = writableDatabase
        val result = db.delete("Asiento", "numeroDeAsiento = ?", arrayOf(numeroDeAsiento.toString()))
        db.close()
        return result > 0
    }
}


