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
    println("TEST 6 - $test6")
    val test7 = calculator.calculate("10  * (1  + 1) * 0.5 * 0 + (100 * (200 + 3) / 271)")
     println(test7)
    println("(200 * ((10 + 3 * 10 * (37 / 2 + 1 * (1 + 1) / 20 - (7 -1))) + (368+2))) - 1000")
     val test8 = calculator.calculate("(200 * ((10 - 3 * 10 * (38 / 2 + 1 * (1 + 1) / 20 - (7 -1))) + (8+2))) - 10")
    println(test8)
   // val test9 = calculator.calculate(readLine().toString())




}


class Calculator {

    private val regFindNum = Regex("[-*/+]")
    private val regFindSigns = Regex("(?<=[^-+*/=,{ ])[-+*/=]")  //(?<=[^-+*/=,{ ])[-+*/=]
    private val regexSpace = Regex("[\\s]")
    private val priorityMap = mapOf(

        "+" to 1,
        "-" to 1,
        "*" to 2,
        "/" to 2
    )

    //counting without braces
    fun calculate(text: String?): String {


        val trimText = toTrimText(text)
        for (i in trimText) {
            if (!i.isCorrectSymbol && !i.isDigit()) {
                throw Exception("You have entered wrong symbol [$i], try again please")
            }
        }

        val listNumbers = toListNumbers(trimText).toMutableList()
        val listSigns = toListSigns(trimText).toMutableList()

        //Final counting here
        while (listSigns.isNotEmpty()) {

            val index = getIndexOperation(listSigns)
            val left = listNumbers.removeAt(index).toDouble()
            val right = listNumbers.removeAt(index).toDouble()
            val operation = listSigns.removeAt(index)
            val result = calcOneOperation(left, operation, right)
            listNumbers.add(index, result)

        }

        return listNumbers.last()
    }

//        val finalResuln: String
//
//        if (text != null) {
//            if (text.contains('(')) {
//                finalResuln = calcWithBraces(text)
//            } else finalResuln = calcWithOutBraces(text)
//
//        }
//return
//    }

    // 1. making list of signs and numbers
//    private fun calcWithOutBraces(text: String?): String {
//
//
//    }

    private fun toTrimText(text: String?): String {
        return text?.trim()
            ?.replace(regexSpace, "") ?: "Oops smth went wrong"
    }

    private fun toListSigns(textAfterTrim: String): List<String> {
        return textAfterTrim.split(regFindSigns)
            .filter { it.isNotEmpty() }
    }

    private fun toListNumbers(textAfterTrim: String): List<String> {

        val list = textAfterTrim.split(regFindNum)
            .filter { it.isNotEmpty() }



        for (i in list) {

            val charharArray = i.toCharArray()
            var counterDots = 0

            for (k in charharArray) {

                if (k == '.') {
                    counterDots++  // проверка на количество точек в числе

                }
                if (counterDots == 2) {
                    throw Exception("Incorrect dots count [$counterDots] in one number $i")
                }

            }
        }

        return list
    }

    // Extract the index of operation
    private fun getIndexOperation(list: List<String>): Int {

        var index = -1
        var currentPriority = -1

        for (i in list) {
            if (currentPriority < priorityMap.getValue(i)) {
                currentPriority = priorityMap.getValue(i)
                index = list.indexOf(i)

            }
        }
        return index
    }


    //counting one operation
    private fun calcOneOperation(left: Double, operation: String, right: Double): String {

        val resultOneOperation = when (operation) {
            "*" -> (left * right).toString()
            "/" -> (left / right).toString()
            "+" -> (left + right).toString()
            "-" -> (left - right).toString()
            else -> "Error: incorrect sign was entered, try again please"
        }

        return if (resultOneOperation == "Infinity") {
            throw Exception("Impossible divide by ZERO")
        } else

            resultOneOperation

    }


    //counting with a braces
    private fun calcWithBraces(text: String): String {

        var checkBracesText = text.trim()
            .replace(regexSpace, "")
        //  println(checkBracesText)
        var isSorted = false

        while (!isSorted) {

            var braceCounter = 0
            var openBrace = 0
            var closeBrace = checkBracesText.indexOf(')')

            for (i in closeBrace downTo 0) {
                if (checkBracesText[i] == '(') {
                    openBrace = i
                    println("Index i=$i closeBrace=$closeBrace  openBrace i=$openBrace checkBracesText[i]=${checkBracesText[i]} checkBracesText[close]=${checkBracesText[closeBrace]}")
                    break
                }

            }


            val subText = checkBracesText.substring(openBrace + 1, closeBrace)
            var bufferText = ""
            println("subText = $subText")

            if (subText.contains(')')) {
                bufferText = calcWithBraces(subText)
            } else {
                bufferText = subText
            }
            println("checkBracesText before replaced = $checkBracesText")
            checkBracesText = checkBracesText.replace("($subText)", calculate(bufferText))
            println("checkBracesText after replaced = $checkBracesText")

            if (!checkBracesText.contains('(') && !checkBracesText.contains(')')) {
                isSorted = true
            }

        }
        return calculate(checkBracesText)


    }


}




