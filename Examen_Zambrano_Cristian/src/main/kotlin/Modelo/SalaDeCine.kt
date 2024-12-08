package ec.edu.epn.jdbc.Modelo

import java.io.Serializable

data class SalaDeCine(
    var nombre: String,               // Nombre de la sala
    var numeroDeAsientos: Int,        // Número total de asientos
    var habilitada: Boolean,          // ¿Está habilitada para uso?
    var apertura: String,             // Horario de apertura
    var tarifaPorAsiento: Double,     // Tarifa estándar por asiento
) : Serializable  {
    override fun toString(): String {
        return """
            Sala de Cine:
            - Nombre: $nombre
            - Número de asientos: $numeroDeAsientos
            - Habilitada: ${if (habilitada) "Sí" else "No"}
            - Horario de apertura: $apertura
            - Tarifa por asiento: $tarifaPorAsiento
          """
    }
}
