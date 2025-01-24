package com.example.prueba_iib
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class ActivityAgregarEditarSalaDeCine : AppCompatActivity() {
    private lateinit var controlador: EntityController
    private var sala_de_cine_seleccionada: Int? = null

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.main),
            texto,
            Snackbar.LENGTH_LONG
        )
        snack.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_editar_sala_de_cine)
        controlador = EntityController(this)

        val nombre_de_la_sala_de_cine = findViewById<EditText>(R.id.nombre_de_la_sala_de_cine)
        val numero_de_asientos = findViewById<EditText>(R.id.numero_de_asientos)
        val habilitada = findViewById<EditText>(R.id.habilitada)/////////////////////
        val horario_apertura = findViewById<EditText>(R.id.horario_apertura)
        val btn_guardar_sala_de_cine = findViewById<Button>(R.id.btn_guardar_sala_de_cine)

        sala_de_cine_seleccionada = intent.getStringExtra("salaDeCineID")?.toInt()
        sala_de_cine_seleccionada?.let { id ->
            val sala = controlador.listarSalasDeCine().find { it.id == id }
            sala?.let {
                nombre_de_la_sala_de_cine.setText(it.nombre_de_la_sala_de_cine.toString())
                numero_de_asientos.setText(it.numero_de_asientos.toString())
                //habilitada.setText(it.habilitada.toString())//////////////////////
                habilitada.setText(if (it?.habilitada == true) "Si" else "No")
                horario_apertura.setText(it.horario_apertura.toString())
            }
        }

        btn_guardar_sala_de_cine.setOnClickListener {
            val nombre_de_la_sala_de_cine_text = nombre_de_la_sala_de_cine.text.toString()
            val numero_de_asientos_text = numero_de_asientos.text.toString()
            val habilitada_text = habilitada.text.toString().toLowerCase() == "si"
            val horario_apertura_text = horario_apertura.text.toString()

            if (nombre_de_la_sala_de_cine_text.isNotBlank() && numero_de_asientos_text.isNotBlank() && habilitada_text.toString().isNotBlank() && horario_apertura_text.isNotBlank()) {
                if (sala_de_cine_seleccionada != null) {
                    controlador.actualizarSalaDeCine(
                        SalaDeCine(sala_de_cine_seleccionada!!, nombre_de_la_sala_de_cine_text, numero_de_asientos_text.toInt(), habilitada_text, horario_apertura_text)
                    )
                    mostrarSnackbar("Sala actualizada")
                } else {
                    val id_iterable = (controlador.listarSalasDeCine().maxOfOrNull { it.id } ?: 0) + 1
                    controlador.crearSalaDeCine(
                        SalaDeCine(id_iterable, nombre_de_la_sala_de_cine_text, numero_de_asientos_text.toInt(), habilitada_text, horario_apertura_text)

                    )
                    mostrarSnackbar("Sala creada")
                }
                finish()
            } else {
                mostrarSnackbar("Todos los campos son obligatorios")
            }
        }
    }
}
