package com.example.prueba_iib

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.main),
            texto,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Sala de cine
        val botonBuscarSalaDeCine = findViewById<Button>(R.id.btn_buscar_sala_de_cine)
        botonBuscarSalaDeCine.setOnClickListener {
            val nombreDeLaSalaDeCine = findViewById<EditText>(R.id.nombre_de_la_sala_de_cine)
            val numerosDeAsientos = findViewById<EditText>(R.id.numeros_de_asientos)
            val habilitada = findViewById<EditText>(R.id.habilitada)
            val horarioApertura = findViewById<EditText>(R.id.horario_apertura)

            EBaseDeDatos.tablaSalaDeCine = ESqliteHelperSalaDeCine(this)
            val salaDeCine = EBaseDeDatos.tablaSalaDeCine!!.consultarSalaDeCinePorNombre(nombreDeLaSalaDeCine.text.toString())
            //val asientos TODO: falta esto
            if (salaDeCine == null) {
                nombreDeLaSalaDeCine.setText("")
                numerosDeAsientos.setText("")
                habilitada.setText("")
                horarioApertura.setText("")
                //asientos.setText("")
                mostrarSnackbar("Sala de cine no encontrada")
            } else {
                nombreDeLaSalaDeCine.setText(salaDeCine.nombreDeLaSalaDeCine)
                numerosDeAsientos.setText(salaDeCine.numeroDeAsientos)
                habilitada.setText(salaDeCine.habilitada.toString())
                horarioApertura.setText(salaDeCine.horarioApertura)
                //asientos.setText(salaDeCine.asientos)
                mostrarSnackbar("${salaDeCine.nombreDeLaSalaDeCine} encontrada")
            }
        }

        val botonEliminarSalaDeCine = findViewById<Button>(R.id.btn_eliminar_sala_de_cine)
        botonEliminarSalaDeCine.setOnClickListener {
            val nombreDeLaSalaDeCine = findViewById<EditText>(R.id.nombre_de_la_sala_de_cine)
            val respuesta = EBaseDeDatos.tablaSalaDeCine!!.eliminarSalaDeCinePorNombre(nombreDeLaSalaDeCine.text.toString())
            if (respuesta) mostrarSnackbar("Sala eliminado") else mostrarSnackbar("Sala no encontrada")
        }

        val botonCrearSalaDeCine = findViewById<Button>(R.id.btn_ingresar_sala_de_cine)
        botonCrearSalaDeCine.setOnClickListener {
            val nombreDeLaSalaDeCine = findViewById<EditText>(R.id.nombre_de_la_sala_de_cine)
            val numerosDeAsientos = findViewById<EditText>(R.id.numeros_de_asientos)
            val habilitada = findViewById<EditText>(R.id.habilitada)
            val horarioApertura = findViewById<EditText>(R.id.habilitada)
            //val asientos //TODO:lamparisima
            val numerosDeAsientosInt = numerosDeAsientos.text.toString().toIntOrNull() ?: 0
            val habilitadaBoolean = habilitada.text.toString().toBooleanStrictOrNull() ?: false

            val respuesta = EBaseDeDatos.tablaSalaDeCine!!.crearSalaDeCine(
                nombreDeLaSalaDeCine.text.toString(),
                numerosDeAsientosInt,
                habilitadaBoolean,
                horarioApertura.text.toString(),
                //asientos.text.toString()
            )
            if (respuesta) mostrarSnackbar("Sala creada") else mostrarSnackbar("Fallo al crear la sala")
        }

        val botonActualizarSalaDeCine = findViewById<Button>(R.id.btn_actualizar_sala_de_cine)
        botonActualizarSalaDeCine.setOnClickListener {
            val nombreDeLaSalaDeCine = findViewById<EditText>(R.id.nombre_de_la_sala_de_cine)
            val numerosDeAsientos = findViewById<EditText>(R.id.numeros_de_asientos)
            val habilitada = findViewById<EditText>(R.id.habilitada)
            val horarioApertura = findViewById<EditText>(R.id.horario_apertura)
            //val asientos //TODO:lamparisima
            val numerosDeAsientosInt = numerosDeAsientos.text.toString().toIntOrNull() ?: 0
            val habilitadaBoolean = habilitada.text.toString().toBooleanStrictOrNull() ?: false
            val respuesta = EBaseDeDatos.tablaSalaDeCine!!.actualizarSalaDeCinePorNombre(
                nombreDeLaSalaDeCine.text.toString(),
                numerosDeAsientosInt,
                habilitadaBoolean,
                horarioApertura.text.toString(),
                //asientos.text.toString()
            )
            if (respuesta) mostrarSnackbar("Sala actualizada") else mostrarSnackbar("Fallo al actualizar sala")
        }

        // Asientos ########################################################################################################
        val botonBuscarAsiento = findViewById<Button>(R.id.btn_buscar_asiento)
        botonBuscarAsiento.setOnClickListener {
            val numeroDeAsiento = findViewById<EditText>(R.id.numero_de_asiento)
            val ocupado = findViewById<EditText>(R.id.ocupado)
            val tipo = findViewById<EditText>(R.id.tipo)
            val precio = findViewById<EditText>(R.id.precio)
            val fechaReservacion = findViewById<EditText>(R.id.fecha_reservacion)
            
            val numeroAsientoIntval = numeroDeAsiento.text.toString().toIntOrNull() ?: 0

            EBaseDeDatos.tablaAsiento = ESqliteHelperAsiento(this)
            val asiento = EBaseDeDatos.tablaAsiento!!.consultarAsientoPorNumero(numeroAsientoIntval)
            if (asiento == null) {
                numeroDeAsiento.setText("")
                ocupado.setText("")
                tipo.setText("")
                precio.setText("")
                fechaReservacion.setText("")
                mostrarSnackbar("Asiento no encontrado")
            } else {
                numeroDeAsiento.setText(asiento.numeroDeAsiento.toString())
                ocupado.setText(asiento.ocupado.toString())
                tipo.setText(asiento.tipo.toString())
                precio.setText(asiento.precio.toString())
                fechaReservacion.setText(asiento.fechaReservacion.toString())
                mostrarSnackbar("Siento ${asiento.numeroDeAsiento} encontrado")
            }
        }

        val botonEliminarAsiento = findViewById<Button>(R.id.btn_eliminar_asiento)
        botonEliminarAsiento.setOnClickListener {
            val numeroDeAsiento = findViewById<EditText>(R.id.numeros_de_asientos)

            val numeroAsientoIntval = numeroDeAsiento.text.toString().toIntOrNull() ?: 0

            val respuesta = EBaseDeDatos.tablaAsiento!!.eliminarAsientoPorNumero(numeroAsientoIntval)
            if (respuesta) mostrarSnackbar("Asiento eliminado") else mostrarSnackbar("Asiento no encontrado")
        }

        val botonCrearAsiento = findViewById<Button>(R.id.btn_ingresar_asiento)
        botonCrearAsiento.setOnClickListener {

            val numeroDeAsiento = findViewById<EditText>(R.id.numero_de_asiento)
            val ocupado = findViewById<EditText>(R.id.ocupado)
            val tipo = findViewById<EditText>(R.id.tipo)
            val precio = findViewById<EditText>(R.id.precio)
            val fechaReservacion = findViewById<EditText>(R.id.fecha_reservacion)

            val numeroAsientoIntval = numeroDeAsiento.text.toString().toIntOrNull() ?: 0
            val precioDouble = precio.text.toString().toDoubleOrNull() ?: 5.0
            val ocupadoBool = ocupado.text.toString().toBooleanStrictOrNull() ?: false

            val respuesta = EBaseDeDatos.tablaAsiento!!.crearAsiento(
                numeroAsientoIntval,
                ocupadoBool,
                tipo.text.toString(),
                precioDouble,
                fechaReservacion.text.toString()
            )
            if (respuesta) mostrarSnackbar("Asiento creado") else mostrarSnackbar("Fallo al crear asiento")
        }

        val botonActualizarAsiento = findViewById<Button>(R.id.btn_actualizar_asiento)
        botonActualizarAsiento.setOnClickListener {
            val numeroDeAsiento = findViewById<EditText>(R.id.numero_de_asiento)
            val ocupado = findViewById<EditText>(R.id.ocupado)
            val tipo = findViewById<EditText>(R.id.tipo)
            val precio = findViewById<EditText>(R.id.precio)
            val fechaReservacion = findViewById<EditText>(R.id.fecha_reservacion)

            val numeroAsientoIntval = numeroDeAsiento.text.toString().toIntOrNull() ?: 0
            val precioDouble = precio.text.toString().toDoubleOrNull() ?: 5.0
            val ocupadoBool = ocupado.text.toString().toBooleanStrictOrNull() ?: false

            val respuesta = EBaseDeDatos.tablaAsiento!!.actualizarAsientoPorNumero(
                numeroAsientoIntval,
                ocupadoBool,
                tipo.text.toString(),
                precioDouble,
                fechaReservacion.text.toString()
            )
            if (respuesta) mostrarSnackbar("Asiento actualizado") else mostrarSnackbar("Fallo al actualizar asiento")
        }
    }

}