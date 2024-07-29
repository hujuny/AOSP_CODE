package com.yhj.kotlinstudy

import kotlin.reflect.KFunction2

/**
 *    @author : 杨虎军
 *    @date   :  2021/04/10
 *    @desc   :四则计算器
 */

/**
 * input： 3*4
 */
fun main(vararg args: String) {

    println(args.size)
    if (args.size < 3) {
        return showHelp()
    }

    val operators = mapOf(
        //两种写法
//        "+" to plus(args[0].toInt(),args[2].toInt()),
        "+" to ::plus,
        "-" to ::minus,
        "*" to ::times,
        "/" to ::plus,
    )

    val op = args[1]
    val opFunc = operators[op] ?: return showHelp()
//    println(opFunc)

    try {
        println("Input：${args.joinToString(" ")}")
        println("Output：${opFunc(args[0].toInt(), args[2].toInt())}")
    } catch (e: Exception) {
        e.printStackTrace()
        println("Invalid arguments")
    }
}


fun plus(arg0: Int, arg1: Int): Int {
    return arg0 + arg1
}

fun minus(arg0: Int, arg1: Int): Int {
    return arg0 - arg1
}

fun times(arg0: Int, arg1: Int): Int {
    return arg0 * arg1
}

fun div(arg0: Int, arg1: Int): Int {
    return arg0 / arg1
}

fun showHelp() {
    println(
        """
        Simple Calculator:
        Input：3*4
        Output：12
    """.trimIndent()
    )
}
