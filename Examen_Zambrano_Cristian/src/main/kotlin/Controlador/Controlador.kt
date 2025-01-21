package ec.edu.epn.jdbc.Controlador

import ec.edu.epn.jdbc.Modelo.SalaDeCine
import ec.edu.epn.jdbc.Modelo.Persistencia

object Controlador {
    private val archivoSalas = "salas_de_cine.dat"
    private var salas: MutableList<SalaDeCine> = mutableListOf()

    init {
        cargarDatos()
    }

    // CREATE
    fun agregarSala(sala: SalaDeCine) {
        salas.add(sala)
        guardarDatos()
    }

    // READ
    fun listarSalas(): List<SalaDeCine> {
        return salas
    }

    fun buscarSala(nombre: String): SalaDeCine? {
        return salas.find { it.nombre.equals(nombre, ignoreCase = true) }
    }

    // UPDATE
    fun actualizarSala(nombre: String, salaActualizada: SalaDeCine): Boolean {
        val indice = salas.indexOfFirst { it.nombre.equals(nombre, ignoreCase = true) }
        if (indice != -1) {
            salas[indice] = salaActualizada
            guardarDatos()
            return true
        }
        return false
    }

    // DELETE
    fun eliminarSala(nombre: String): Boolean {
        val eliminado = salas.removeIf { it.nombre.equals(nombre, ignoreCase = true) }
        if (eliminado) guardarDatos()
        return eliminado
    }

    // Gestión de persistencia
    private fun guardarDatos() {
        Persistencia.guardarObjeto(salas, archivoSalas)
    }

    private fun cargarDatos() {
        val datosCargados: List<SalaDeCine>? = Persistencia.cargarObjeto(archivoSalas)
        if (datosCargados != null) {
            salas = datosCargados.toMutableList()
        }
    }
}
