package com.example.prueba_iib

class EBaseDeDatos {
    companion object{
        var tablaEntrenador: ESqliteHelperEntrenador? = null
        var tablaSalaDeCine: ESqliteHelperSalaDeCine? = null
        var tablaAsiento: ESqliteHelperAsiento? = null
    }
}
