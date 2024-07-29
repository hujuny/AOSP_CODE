package com.yhj.kotlinstudy.chapter_05

/**
 *    @author : 杨虎军
 *    @date   :  2021/04/14
 *    @desc   : 为String实现四则运算
 */
class Kotlin17 {
}

//operator fun String.minus(right: Any?): String {
//    return this.replaceFirst(right.toString(), "")
//}
operator fun String.minus(right: Any?) = this.replaceFirst(right.toString(), "")

//operator fun String.times(count:Int)= repeat((1..count).count())
operator fun String.times(count: Int) = (1..count).joinToString("") { this }

//operator fun String.div(right: Any?):Int {
//    val right = right.toString()
//   return this.windowed(right.length,  1) {
//        it == right
//    }.count { it }
//}

//lambda表达式
operator fun String.div(right: Any?):Int {
    val right = right.toString()
    return this.windowed(right.length,  1,transform = {it==right}).count{it}
}


fun main() {
    val value = "helloWorld"
    println(value - "hello")
    println("*" * 10)
    println(value / "l")
}