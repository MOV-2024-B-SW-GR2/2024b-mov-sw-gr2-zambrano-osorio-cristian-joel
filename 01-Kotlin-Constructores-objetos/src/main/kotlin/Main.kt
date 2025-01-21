package ec.edu.epn.jdbc

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val sumaA = Suma(1,1)
    val sumaB = Suma(null, 1)
    val sumaC = Suma(1, null)
    val sumaD = Suma(null, null)
    sumaA.sumar()
    sumaB.sumar()
    sumaC.sumar()
    sumaD.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)
}

class Suma {
    constructor( // Constructor secundario
        uno: Int,
        dos: Int?, // Entero nullable
    ):this(
        uno,
        if(dos == null) 0 else dos
    )
    constructor( // Constructor secundario
        uno: Int?, // Entero nullable
        dos: Int?,  // Entero nullable
    ):this(
        if(uno == null) 0 else uno,
        if(dos == null) 0 else dos
    )
    val numeroUno = 1
    val numeroDos = 2
    fun sumar ():Int{
        val total = numeroUno + numeroDos
        agregarHistorial(total)
        return total
    }
    companion object { // Comparte entre todas las instancias, similar al STATIC
        // funciones, variables
        // NombreClase.metodo, NombreClase.funcion =>
        // Suma.pi
        val pi = 3.14
        // Suma.elevarAlCuadrado
        fun elevarAlCuadrado(num:Int):Int{ return num * num }
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorTotalSuma:Int){ // Suma.agregarHistorial
            historialSumas.add(valorTotalSuma)
        }
    }
}

