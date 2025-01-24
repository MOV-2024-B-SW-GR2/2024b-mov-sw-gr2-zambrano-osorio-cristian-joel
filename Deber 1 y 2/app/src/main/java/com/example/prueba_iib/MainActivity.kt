package com.example.prueba_iib
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var listViewSalasDeCine: ListView
    private lateinit var btnAgregarSalaDeCine: Button
    private lateinit var controlador: EntityController
    private var sala_de_cine_seleccionada: SalaDeCine? = null

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.main),
            texto,
            Snackbar.LENGTH_LONG
        )
        snack.show()
    }

    private fun actualizarLista() {
        val salas_de_cine = controlador.listarSalasDeCine()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, salas_de_cine.map { it.nombre_de_la_sala_de_cine })
        listViewSalasDeCine.adapter = adapter
        listViewSalasDeCine.setOnItemClickListener { _, _, position, _ ->
            sala_de_cine_seleccionada = salas_de_cine[position]
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        controlador = EntityController(this)
        listViewSalasDeCine = findViewById(R.id.listViewSalasDeCine)
        btnAgregarSalaDeCine = findViewById(R.id.btnAgregarSalaDeCine)

        btnAgregarSalaDeCine.setOnClickListener {
            val intent = Intent(this, ActivityAgregarEditarSalaDeCine::class.java)
            startActivity(intent)
        }

        registerForContextMenu(listViewSalasDeCine)
        actualizarLista()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_salas_de_cine, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as? AdapterView.AdapterContextMenuInfo
        val salaDeCineIndex = info?.position
        sala_de_cine_seleccionada = salaDeCineIndex?.let { controlador.listarSalasDeCine()[it] }

        when (item.itemId) {
            R.id.context_ver_asientos -> {
                sala_de_cine_seleccionada?.let {
                    val intent = Intent(this, ActivityListarAsientos::class.java)
                    intent.putExtra("salaDeCineID", it.id.toString())
                    startActivity(intent)
                }
            }
            R.id.context_editar_sala_de_cine -> {
                sala_de_cine_seleccionada?.let {
                    val intent = Intent(this, ActivityAgregarEditarSalaDeCine::class.java)
                    intent.putExtra("salaDeCineID", it.id.toString())
                    startActivity(intent)
                }
            }
            R.id.context_eliminar_sala_de_cine -> {
                sala_de_cine_seleccionada?.let {
                    controlador.eliminarSalaDeCine(it.id)
                    mostrarSnackbar("Sala eliminada")
                    actualizarLista()
                } ?: mostrarSnackbar("Error al eliminar sala")
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        actualizarLista()
    }
}