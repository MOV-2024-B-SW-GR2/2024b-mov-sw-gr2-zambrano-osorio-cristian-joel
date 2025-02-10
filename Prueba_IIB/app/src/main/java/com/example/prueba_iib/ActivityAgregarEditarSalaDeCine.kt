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

        // Campos existentes
        val nombre_de_la_sala_de_cine = findViewById<EditText>(R.id.nombre_de_la_sala_de_cine)
        val numero_de_asientos = findViewById<EditText>(R.id.numero_de_asientos)
        val habilitada = findViewById<EditText>(R.id.habilitada)
        val horario_apertura = findViewById<EditText>(R.id.horario_apertura)

        // Nuevos campos
        val latitud = findViewById<EditText>(R.id.latitud)
        val longitud = findViewById<EditText>(R.id.longitud)

        val btn_guardar_sala_de_cine = findViewById<Button>(R.id.btn_guardar_sala_de_cine)

        // Cargar datos existentes (si es edición)
        sala_de_cine_seleccionada = intent.getStringExtra("salaDeCineID")?.toInt()
        sala_de_cine_seleccionada?.let { id ->
            val sala = controlador.listarSalasDeCine().find { it.id == id }
            sala?.let {
                nombre_de_la_sala_de_cine.setText(it.nombre_de_la_sala_de_cine)
                numero_de_asientos.setText(it.numero_de_asientos.toString())
                habilitada.setText(if (it.habilitada) "Si" else "No")
                horario_apertura.setText(it.horario_apertura)
                latitud.setText(it.latitud.toString())    // Cargar latitud
                longitud.setText(it.longitud.toString())    // Cargar longitud
            }
        }

        btn_guardar_sala_de_cine.setOnClickListener {
            // Validar campos
            val nombre = nombre_de_la_sala_de_cine.text.toString()
            val asientos = numero_de_asientos.text.toString()
            val habilitadaValue = habilitada.text.toString().equals("si", ignoreCase = true)
            val horario = horario_apertura.text.toString()
            val lat = latitud.text.toString()
            val lon = longitud.text.toString()

            if (nombre.isBlank() || asientos.isBlank() || horario.isBlank() || lat.isBlank() || lon.isBlank()) {
                mostrarSnackbar("Todos los campos son obligatorios")
                return@setOnClickListener
            }

            // Validar coordenadas
            val latitudValue = lat.toDoubleOrNull() ?: run {
                mostrarSnackbar("Latitud inválida")
                return@setOnClickListener
            }

            val longitudValue = lon.toDoubleOrNull() ?: run {
                mostrarSnackbar("Longitud inválida")
                return@setOnClickListener
            }

            // Validar rangos geográficos
            if (latitudValue !in -90.0..90.0 || longitudValue !in -180.0..180.0) {
                mostrarSnackbar("Coordenadas inválidas (Lat: -90 a 90, Lon: -180 a 180)")
                return@setOnClickListener
            }

            if (sala_de_cine_seleccionada != null) {
                // Actualizar
                val salaActualizada = SalaDeCine(
                    id = sala_de_cine_seleccionada!!,
                    nombre_de_la_sala_de_cine = nombre,
                    numero_de_asientos = asientos.toInt(),
                    habilitada = habilitadaValue,
                    horario_apertura = horario,
                    latitud = latitudValue,    // Incluir latitud
                    longitud = longitudValue   // Incluir longitud
                )
                controlador.actualizarSalaDeCine(salaActualizada)
                mostrarSnackbar("Sala actualizada")
            } else {
                // Crear nueva
                val nuevaSala = SalaDeCine(
                    id = (controlador.listarSalasDeCine().maxOfOrNull { it.id } ?: 0) + 1,
                    nombre_de_la_sala_de_cine = nombre,
                    numero_de_asientos = asientos.toInt(),
                    habilitada = habilitadaValue,
                    horario_apertura = horario,
                    latitud = latitudValue,    // Incluir latitud
                    longitud = longitudValue    // Incluir longitud
                )
                controlador.crearSalaDeCine(nuevaSala)
                mostrarSnackbar("Sala creada")
            }
            finish()
        }
    }
}
