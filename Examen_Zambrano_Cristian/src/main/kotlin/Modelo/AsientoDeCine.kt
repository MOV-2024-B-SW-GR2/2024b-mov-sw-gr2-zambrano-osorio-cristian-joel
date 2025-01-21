package ec.edu.epn.jdbc.Modelo

import java.io.Serializable

data class AsientoDeCine(
    var numero: Int,                  // Número del asiento
    var ocupado: Boolean,             // ¿Está ocupado?
    var tipo: String,                 // Tipo de asiento (ej. VIP, Estándar)
    var precio: Double,               // Precio del asiento
    var fechaReservacion: String?     // Fecha de reservación (si está ocupado)
) : Serializable
