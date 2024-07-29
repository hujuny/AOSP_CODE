package com.yhj.kotlinstudy

import com.yhj.kotlinstudy.chapter_07.Pair

/**
 *    @author : 杨虎军
 *    @date   :  2021/04/13
 *    @desc   : 运算符和中缀表达式
 */
class Kotlin14 {


}

fun main() {


    /**
     * 运算符
     * kotlin支持运算符重载
     * 范围仅限官方指定的符号
     * 官方文档
     * https://kotlinlang.org/docs/operator-overloading.html#augmented-assignments
     */
    /**
     * ==和equals 相等
     */
//    "hello"=="world"
//    "hello".equals("world")

    /**
     * +与plus
     */
    2.plus(3)
    plus(2, 3)
    /**
     * in与contains
     */
    val list = listOf(1, 2, 3, 4)

    if (2 in list) {
        println("存在即合理")
    }

    for (i in list.indices) {

    }
    /**
     * []与get
     */
    val maps = mapOf<String, Any>("name" to "yhj")
    val pair = Pair<String, Any>("23", 2)
    pair.first
    val value = maps["hello"]

    /**
     * >与compareTo
     */
    val b = 2.compareTo(3) < 0
    val f = 2 < 3

    /**
     *()与invoke
     */

    val func = fun() {
        println("hello")
    }

    func()

//    func.invoke()=func()

    /**
     * 中缀表达式
     */
    2 to 3
    2.to(3)

//    infix 关键字可以简写
    //函数体为一个表达式时，可直接写作简化的形式
    Pair<Int, String>(2, "aa")

    infix fun String.rotate(count: Int): String {
        val i = count % length
        return this.substring(i) + this.substring(0, i)
    }

    println("hello" rotate 5)

    class Book
    class Desk

    infix fun Book.on(desk: Desk) {

    }
    Book() on Desk()
    //DSL
}

class Complex(var real: Double, var image: Double) {


    /**
     * 自定义运算符重载
     */
    operator fun Complex.minus(real: Double): Complex {
        return Complex(this.real - real, this.image)
    }
}



