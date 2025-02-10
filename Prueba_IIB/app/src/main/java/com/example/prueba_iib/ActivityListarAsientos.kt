package com.example.prueba_iib

import android.app.Activity
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

class ActivityListarAsientos : AppCompatActivity() {
    var asientos = ArrayList<Asiento>()
    private lateinit var listViewAsientos: ListView
    private lateinit var btn_agregar_asiento: Button
    private lateinit var controlador: EntityController
    private var asiento_seleccionado: Asiento? = null
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
        setContentView(R.layout.activity_listar_asientos)
        controlador = EntityController(this)
        listViewAsientos = findViewById(R.id.listViewAsientos)
        btn_agregar_asiento = findViewById(R.id.btn_agregar_asiento)
        sala_de_cine_seleccionada = intent.getStringExtra("salaDeCineID")?.toInt()

        btn_agregar_asiento.setOnClickListener {
            val intent = Intent(this, ActivityAgregarEditarAsiento::class.java)
            intent.putExtra("salaDeCineID", sala_de_cine_seleccionada.toString())
            startActivity(intent)
        }
        registerForContextMenu(listViewAsientos)
        actualizarLista()
    }


    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_asiento, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as? AdapterView.AdapterContextMenuInfo
        val position = info?.position
        asiento_seleccionado = position?.let{controlador.listarAsientosPorSalaDeCine(sala_de_cine_seleccionada!!)[it]}
        when (item.itemId) {
            R.id.context_editar_asiento -> {
                val intent = Intent(this, ActivityAgregarEditarAsiento::class.java)
                intent.putExtra("asientoID", asiento_seleccionado!!.id.toString())
                intent.putExtra("salaDeCineID", sala_de_cine_seleccionada.toString())
                startActivity(intent)
            }
            R.id.context_eliminar_asiento -> {
                val respuesta = controlador.eliminarAsiento(asiento_seleccionado!!.id)
                if (respuesta) {
                    mostrarSnackbar("Asiento eliminado")
                    actualizarLista()
                } else {
                    mostrarSnackbar("Error al eliminar Asiento")
                }
            }
        }
        return super.onContextItemSelected(item)
    }

    private fun actualizarLista() {
        asientos = controlador.listarAsientosPorSalaDeCine(sala_de_cine_seleccionada!!)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, asientos.map { it.tipo })
        listViewAsientos.adapter = adapter
        listViewAsientos.setOnItemClickListener { _, _, position, _ ->
            asiento_seleccionado = asientos[position]
        }
    }
}