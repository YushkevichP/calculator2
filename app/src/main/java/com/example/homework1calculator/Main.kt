package com.example.homework1calculator

fun main() {

    val calculator = Calculator()


    val test1 = calculator.calculate("5 -5")
    println(test1)
    val test2 = calculator.calculate("5 * 5 / (10-4)")
    println(test2)
    val test3 = calculator.calculate("10 * (10 + 0)")
    println(test3)
    val test4 = calculator.calculate("5 + 15 / 3 + 7 * 9")
    println(test4)
    val test6 = calculator.calculate("(5+5+20) / (2 * (7-2) * 9)")
    println(test6)
    val test7 = calculator.calculate("10  * (1  + 1) * 0.5 * 0 + (100 * (200 + 3) / 271)")
    println(test7)
    val test8 =
        calculator.calculate("(200 * ((10 + 3 * 10 * (38 / 2 + 1 * (1 + 1) / 20 - (7 -1))) + (8+2))) - 10")
    println(test8)


}