package com.yhj.kotlinstudy

/**
 *    @author : 杨虎军
 *    @date   :  2021/04/09
 *    @desc   : Hello World
 */
fun main() {
    println("你好，kotlin")
    runBlock {
        List(1000) {
            println("hello world")
        }
    }
    val person = Person(18, "yhj")

}

fun runBlock(block: () -> Unit) {
    val start = System.currentTimeMillis()
    block()
    println(System.currentTimeMillis() - start)
}

class Person(var age: Int, val name: String) {

}

