package com.example.prueba_iib
import android.content.Context
import android.content.ContentValues
class EntityController(context: Context) {
    private val dbHelper = ESqliteHelper(context)

    // Crear Sala de Cine (AGREGAR LATITUD Y LONGITUD)
    fun crearSalaDeCine(salaDeCine: SalaDeCine) {
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put("nombre_de_la_sala_de_cine", salaDeCine.nombre_de_la_sala_de_cine)
            put("numero_de_asientos", salaDeCine.numero_de_asientos)
            put("habilitada", salaDeCine.habilitada)
            put("horario_apertura", salaDeCine.horario_apertura)
            put("latitud", salaDeCine.latitud)      // Nuevo campo
            put("longitud", salaDeCine.longitud)    // Nuevo campo
        }
        db.insert("SALA", null, valores)
        db.close()
    }

    // Listar Salas de Cine (LEER LATITUD Y LONGITUD)
    fun listarSalasDeCine(): List<SalaDeCine> {
        val db = dbHelper.readableDatabase
        val posicion = db.rawQuery("SELECT * FROM SALA", null)
        val salasDeCine = arrayListOf<SalaDeCine>()
        while (posicion.moveToNext()) {
            val id = posicion.getInt(posicion.getColumnIndexOrThrow("id"))
            val nombreDeLaSala = posicion.getString(posicion.getColumnIndexOrThrow("nombre_de_la_sala_de_cine"))
            val numeroDeAsientos = posicion.getInt(posicion.getColumnIndexOrThrow("numero_de_asientos"))
            val habilitada = posicion.getInt(posicion.getColumnIndexOrThrow("habilitada")) == 1
            val horarioApertura = posicion.getString(posicion.getColumnIndexOrThrow("horario_apertura"))
            // Nuevos campos
            val latitud = posicion.getDouble(posicion.getColumnIndexOrThrow("latitud"))
            val longitud = posicion.getDouble(posicion.getColumnIndexOrThrow("longitud"))

            salasDeCine.add(
                SalaDeCine(
                    id,
                    nombreDeLaSala,
                    numeroDeAsientos,
                    habilitada,
                    horarioApertura,
                    latitud,     // Agregar al constructor
                    longitud      // Agregar al constructor
                )
            )
        }
        posicion.close()
        db.close()
        return salasDeCine
    }

    // Actualizar Sala de Cine (ACTUALIZAR LATITUD Y LONGITUD)
    fun actualizarSalaDeCine(salaDeCine: SalaDeCine): Boolean {
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put("nombre_de_la_sala_de_cine", salaDeCine.nombre_de_la_sala_de_cine)
            put("numero_de_asientos", salaDeCine.numero_de_asientos)
            put("habilitada", salaDeCine.habilitada)
            put("horario_apertura", salaDeCine.horario_apertura)
            put("latitud", salaDeCine.latitud)      // Nuevo campo
            put("longitud", salaDeCine.longitud)    // Nuevo campo
        }
        val rows = db.update(
            "SALA",
            valores,
            "id = ?",
            arrayOf(salaDeCine.id.toString())
        )
        db.close()
        return rows > 0
    }
    //Eliminar Sala de Cine
    fun eliminarSalaDeCine(salaDeCineID: Int): Boolean {
        val db = dbHelper.writableDatabase
        val filas = db.delete(
            "SALA",
            "id = ?",
            arrayOf(salaDeCineID.toString())
        )
        db.close()
        return filas > 0
    }
    //Crear un asiento
    fun crearAsiento(asiento: Asiento) {
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put("ocupado", asiento.ocupado)
            put("tipo", asiento.tipo)
            put("precio", asiento.precio)
            put("sala_de_cine_id", asiento.sala_de_cine_id)
        }
        db.insert("ASIENTO", null, valores)
        db.close()
    }
    //Listar asientos de una sala de cine
    fun listarAsientosPorSalaDeCine(salaDeCineID: Int): ArrayList<Asiento> {
        val db = dbHelper.writableDatabase
        val posicion = db.rawQuery("SELECT * FROM ASIENTO WHERE sala_de_cine_id = ?", arrayOf(salaDeCineID.toString()))
        val asientos = arrayListOf<Asiento>()
        while (posicion.moveToNext()) {
            val id = posicion.getInt(0)
            val ocupado = posicion.getInt(1) == 1
            val tipo = posicion.getString(2)
            val precio = posicion.getDouble(3)
            val sala_de_cine_id = posicion.getInt(4)
            asientos.add(Asiento(id, ocupado, tipo, precio, sala_de_cine_id))
        }
        posicion.close()
        db.close()
        return asientos
    }
    //Actualizar asiento
    fun actualizarAsiento(asiento: Asiento): Boolean {
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put("ocupado", asiento.ocupado)
            put("tipo", asiento.tipo)
            put("precio", asiento.precio)
        }
        val query = db.update(
            "ASIENTO",
            valores,
            "id = ?",
            arrayOf(asiento.id.toString())
        )
        db.close()
        return query > 0
    }
    //Eliminar Asiento
    fun eliminarAsiento(asientoID: Int): Boolean {
        val db = dbHelper.writableDatabase
        val filas = db.delete(
            "ASIENTO",
            "id = ?",
            arrayOf(asientoID.toString())
        )
        db.close()
        return filas > 0
    }
    //Mostrar Asientos
    fun mostrarAsientos() {
        val db = dbHelper.readableDatabase
        val posicion = db.rawQuery("SELECT * FROM ASIENTO", null)
        while (posicion.moveToNext()) {
            val id = posicion.getInt(posicion.getColumnIndexOrThrow("id"))
            val ocupado = posicion.getInt(posicion.getColumnIndexOrThrow("ocupado")) == 1
            val tipo = posicion.getString(posicion.getColumnIndexOrThrow("tipo"))
            val precio = posicion.getDouble(posicion.getColumnIndexOrThrow("precio"))
            val sala_de_cine_id = posicion.getInt(posicion.getColumnIndexOrThrow("sala_de_cine_id"))
            println("ID: $id, Estado: ${if (ocupado) "Ocupado" else "Libre"}, Tipo: $tipo, Precio: $precio, Sala ID: $sala_de_cine_id")
        }
        posicion.close()
        db.close()
    }
}