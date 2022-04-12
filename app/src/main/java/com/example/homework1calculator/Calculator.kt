package com.example.homework1calculator


class Calculator {

    private val regFindNum = Regex("[-*/+]")
    private val regFindSigns = Regex("[^-+*/=]")  //(?<=[^-+*/=,{ ])[-+*/=]
    private val regexSpace = Regex("[\\s]")
    private val priorityMap = mapOf(

        "+" to 1,
        "-" to 1,
        "*" to 2,
        "/" to 2
    )

    fun calculate(text: String?): String {

        return text?.let {          // функция расширения .let http://developer.alexanderklimov.ru/android/kotlin/with-apply-also.php
            if (it.contains('(')){
                calcWithBraces(it)
            } else {calculateWithNoBraces(it)}
        }?:""

//        var finalResult = ""
//
//        if (text != null) {
//            if (text.contains('(')) {
//                finalResult = calcWithBraces(text)
//
//            } else finalResult = calculateWithNoBraces(text)
//
//        }
//
//        return finalResult

    }
    //counting without braces
    private fun calculateWithNoBraces(text: String?): String {

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
            listNumbers.add(index, result.toString())

        }

        return listNumbers.last()
    }


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
    private fun calcOneOperation(left: Double, operation: String, right: Double): Double {

        val resultOneOperation = when (operation) {
            "*" -> (left * right)
            "/" -> (left / right)
            "+" -> (left + right)
            "-" -> (left - right)
            else -> throw Exception("smth went wrong")
        }

        return if (resultOneOperation.toString() == "Infinity") {
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
                    //  println("Index i=$i closeBrace=$closeBrace  openBrace i=$openBrace checkBracesText[i]=${checkBracesText[i]} checkBracesText[close]=${checkBracesText[closeBrace]}")
                    break
                }

            }

            val subText = checkBracesText.substring(openBrace + 1, closeBrace)
            var bufferText = ""
            //println("subText = $subText")

            if (subText.contains(')')) bufferText = calcWithBraces(subText) else bufferText = subText
            // println("checkBracesText before replaced = $checkBracesText")
            checkBracesText =
                checkBracesText.replace("($subText)", calculateWithNoBraces(bufferText))
            // println("checkBracesText after replaced = $checkBracesText")

            if (!checkBracesText.contains('(') && !checkBracesText.contains(')')) {
                isSorted = true
            }

        }
        return calculateWithNoBraces(checkBracesText)
    }
}




