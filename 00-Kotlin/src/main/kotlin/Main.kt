package ec.edu.epn.jdbc
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {

    //Variable mutable
    var edad = 18
    //println(edad) //primer valor
    edad = 54 //cambiar valor
    //println(edad) //valor cambiado

    //Variable inmutable
    val nombre = "Juan"
    //println(nombre) //primer valor
    //nombre = "Pedro" //Error: Val cannot be reassigned

    //DuckTyping
    val duck = 1
    val numero:Int = 1

    var estadoCivil = 'G'

    when(estadoCivil){
        ('S') -> println("Soltero")
        ('C') -> println("Casado")
        ('D') -> println("Divorciado")
        ('V') -> println("Viudo")
    }

    //Operador ternario
    val esSoltero = true
    val coqueteo:String = if (esSoltero) "Si" else "No"

    //Funciones
    fun imprimirNombre(nombre:String):Unit{
        println(nombre)
        fun imprimirDeNuevo(nombre:String):Unit{
            println(nombre)
        }
    }

    fun templateStrings(nombre:String):Unit{
        println("Mi nombre es $nombre")
        val segundoNombre = "Pablo"
        println("Nombre:  ${nombre.plus(segundoNombre)}")
    }

}

