package com.syedsubahani.workspacehub.common

fun main(args: Array<String>) {
    var gender:String? = null

    gender?.toUpperCase()

    println(gender?.toUpperCase())

    var selectedGender:String = gender?:"Male"

    println(selectedGender)

    gender?.let {
        println("Line 1")
        println("Line 2")
        println(it)
    }

    var value:String? = gender?.toUpperCase()
    println(value)
}