package com.yhj.kotlinstudy.chapter_05

import java.lang.Exception

/**
 *    @author : 杨虎军
 *    @date   :  2021/04/13
 *    @desc   :分支表达式
 */
class Kotlin13 {

    fun main() {

    }
}

fun main() {
    /**
     * if else 表达式
     */
    if (false) {

    } else {

    }
//    c=a==3?4:5
//    c = if (a == 3) 4 else 5
    val b = 9


    /**
     * when 对应java的switch case
     */
    val a: Int = 10
    var c: Int
//    when (a) {
//        0 -> c = 5
//        1 -> c = 100
//        else -> c = 88
//    }
    c = when (a) {
        0 -> 5
        1 -> 100
        else -> 88
    }

    val x: Any = "111"
    //条件转移到分支
//    when{
//        x is String->c=x.length
//        x==1->c=100
//        else->c=20
//    }

    c = when (x) {
        is String -> x.length
        1 -> 100
        else -> 20
    }

    c = when (val input = readLine()) {
        null -> 0
        else -> input.length
    }
    /**
     * try ...catch
     * kotlin没有受检异常
     * 受检异常会让函数调用强制产生副作用
     */
//    try {
//        c=a/b
//    }catch (e:Exception){
//        e.printStackTrace()
//        c=0
//    }
    c = try {
        a / b
    } catch (e: Exception) {
        e.printStackTrace()
        0
    }
}