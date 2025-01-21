package ec.edu.epn.jdbc.Modelo

import java.io.*

object Persistencia {
    fun guardarObjeto(objeto: Any, archivo: String) {
        ObjectOutputStream(FileOutputStream(archivo)).use { it.writeObject(objeto) }
    }

    fun <T> cargarObjeto(archivo: String): T? {
        return try {
            ObjectInputStream(FileInputStream(archivo)).use { it.readObject() as T }
        } catch (e: Exception) {
            null
        }
    }
}
