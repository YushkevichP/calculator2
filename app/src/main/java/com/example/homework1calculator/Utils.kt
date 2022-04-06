package com.example.homework1calculator




val Char.isCorrectSymbol
    get() = when (this) {
        '*' -> true
        '/' -> true
        '+' -> true
        '-' -> true
        '.' -> true
        '(' -> true
        ')' -> true
        else -> false
    }
