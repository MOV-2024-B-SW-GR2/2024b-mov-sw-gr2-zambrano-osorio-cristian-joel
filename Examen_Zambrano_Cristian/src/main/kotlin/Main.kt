package ec.edu.epn.jdbc

import ec.edu.epn.jdbc.Controlador.Controlador
import ec.edu.epn.jdbc.Modelo.AsientoDeCine
import ec.edu.epn.jdbc.Modelo.SalaDeCine
import java.util.Date

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    while (true) {
        println("\n--- Sistema de Gestión de Salas de Cine ---")
        println("1. Crear sala")
        println("2. Listar salas")
        println("3. Buscar sala")
        println("4. Actualizar sala")
        println("5. Eliminar sala")
        println("6. Salir")
        print("Selecciona una opción: ")

        when (readln().toIntOrNull()) {
            1 -> {
                print("Nombre de la sala: ")
                val nombre = readln()
                print("Número de asientos: ")
                val numeroDeAsientos = readln().toIntOrNull() ?: 0
                print("¿Está habilitada? (true/false): ")
                val habilitada = readln().toBoolean()
                print("Horario de apertura: ")
                val apertura = readln()
                print("Tarifa por asiento: ")
                val tarifa = readln().toDoubleOrNull() ?: 0.0

                val asientos = mutableListOf<AsientoDeCine>()
                println("\n--- Configuración de Asientos ---")
                for (i in 1..numeroDeAsientos) {
                    println("Configurando asiento #$i:")
                    print("¿Está ocupado? (true/false): ")
                    val ocupado = readln().toBoolean()

                    print("Tipo de asiento (ej: VIP, Estándar): ")
                    val tipo = readln()

                    val precio = if (tipo.lowercase() == "vip") tarifa * 1.5 else tarifa
                    println("Precio asignado: $$precio")

                    val asiento = AsientoDeCine(numero = i, ocupado = ocupado, tipo = tipo, precio = precio, fechaReservacion = Date().toString())
                    asientos.add(asiento)
                }
                val sala = SalaDeCine(nombre, numeroDeAsientos, habilitada, apertura, tarifa, asientos)
                Controlador.agregarSala(sala)
                println("Sala agregada correctamente.")
            }

            2 -> {
                println("\n--- Listado de Salas ---")
                Controlador.listarSalas().forEach { println(it) }
            }

            3 -> {
                print("Nombre de la sala a buscar: ")
                val nombre = readln()
                val sala = Controlador.buscarSala(nombre)
                println(sala ?: "Sala no encontrada.")
            }

            4 -> {
                print("Nombre de la sala a actualizar: ")
                val nombre = readln()
                val sala = Controlador.buscarSala(nombre)
                if (sala != null) {
                    println("Sala encontrada: $sala")
                    print("Nuevo nombre (actual: ${sala.nombre}): ")
                    val nuevoNombre = readln()
                    print("Nuevo número de asientos (actual: ${sala.numeroDeAsientos}): ")
                    val nuevoNumeroDeAsientos = readln().toIntOrNull() ?: sala.numeroDeAsientos
                    print("¿Está habilitada? (actual: ${sala.habilitada}): ")
                    val nuevaHabilitada = readln().toBoolean()
                    print("Nuevo horario de apertura (actual: ${sala.apertura}): ")
                    val nuevaApertura = readln()
                    print("Nueva tarifa por asiento (actual: ${sala.tarifaPorAsiento}): ")
                    val nuevaTarifa = readln().toDoubleOrNull() ?: sala.tarifaPorAsiento

                    val salaActualizada = SalaDeCine(
                        nuevoNombre, nuevoNumeroDeAsientos, nuevaHabilitada,
                        nuevaApertura, nuevaTarifa, sala.asientos
                    )
                    if (Controlador.actualizarSala(nombre, salaActualizada)) {
                        println("Sala actualizada correctamente.")
                    } else {
                        println("No se pudo actualizar la sala.")
                    }
                } else {
                    println("Sala no encontrada.")
                }
            }

            5 -> {
                print("Nombre de la sala a eliminar: ")
                val nombre = readln()
                if (Controlador.eliminarSala(nombre)) {
                    println("Sala eliminada correctamente.")
                } else {
                    println("No se encontró la sala.")
                }
            }

            6 -> {
                println("Saliendo...")
                break
            }

            else -> println("Opción no válida.")
        }
    }
}
