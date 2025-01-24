package com.example.prueba_iib

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class ActivityAgregarEditarAsiento : AppCompatActivity() {
    private lateinit var btn_guardar_asiento: Button
    private lateinit var controlador: EntityController
    private var asiento_seleccionado: Int? = null
    private var sala_de_cine_seleccionada: Int? = null

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.listViewAsientos),
            texto,
            Snackbar.LENGTH_LONG
        )
        snack.show()
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_editar_asiento)
        controlador = EntityController(this)
        val ocupado = findViewById<EditText>(R.id.ocupado)
        val tipo = findViewById<EditText>(R.id.tipo)
        val precio = findViewById<EditText>(R.id.precio)
        btn_guardar_asiento = findViewById(R.id.btn_guardar_asiento)

        sala_de_cine_seleccionada = intent.getStringExtra("salaDeCineID")?.toInt()
        asiento_seleccionado = intent.getStringExtra("asientoID")?.toInt()
        asiento_seleccionado?.let{ id ->
            val asiento = controlador.listarAsientosPorSalaDeCine(sala_de_cine_seleccionada!!).find { it.id == asiento_seleccionado }
            if (asiento != null) {
                ocupado.setText(if (asiento.ocupado) "si" else "no")
                tipo.setText(asiento.tipo.toString())
                precio.setText(asiento.precio.toString())
            }

        }
        btn_guardar_asiento.setOnClickListener {
            val ocupado_text = ocupado.text.toString().toLowerCase() == "si"
            val tipo_text = tipo.text.toString()
            val precio_text = precio.text.toString().toDoubleOrNull()

            if(tipo_text != null && precio_text != null && ocupado_text != null){
                if(asiento_seleccionado != null){
                    controlador.actualizarAsiento(
                        Asiento(asiento_seleccionado!!, ocupado_text, tipo_text, precio_text, sala_de_cine_seleccionada!!)
                    )
                    mostrarSnackbar("Asiento actualizado")
                }else{
                    //val id_iterable = (controlador.listarSalasDeCine().maxOfOrNull { it.id } ?: 0) + 1

                    val asiento_creado = Asiento(0, ocupado_text, tipo_text, precio_text, sala_de_cine_seleccionada!!)
                    controlador.crearAsiento(asiento_creado)
                    mostrarSnackbar("Asiento creado")
                }
                val intent = Intent(this, ActivityListarAsientos::class.java)
                intent.putExtra("salaDeCineID", sala_de_cine_seleccionada.toString())
                startActivity(intent)
            }
        }
    }
}
