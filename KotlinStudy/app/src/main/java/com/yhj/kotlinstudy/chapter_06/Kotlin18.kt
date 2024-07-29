package com.yhj.kotlinstudy.chapter_06

import java.nio.charset.CodingErrorAction

/**
 *    @author : 杨虎军
 *    @date   :  2021/04/14
 *    @desc   : 高阶函数
 */
class Kotlin18 {

    //扩展方法   接收函数类型作为参数
    inline fun IntArray.forEach(action: (Int) -> Unit): Unit {
        for (element in this) action(element)
    }

    //返回值元素类型    接收函数类型作为参数
    inline fun <R> IntArray.map(transform: (Int) -> R): List<R> {

        return mapTo(ArrayList<R>(size), transform)
    }
}

fun main() {
    /**
     * 参数包含函数类型
     */
    fun needsFunction(block: () -> Unit) {
        block()
    }

    /**
     * 返回值为函数类型
     */
    fun returnsFunction(): () -> Long {
        return { System.currentTimeMillis() }
    }
    //参数类型包含函数类型或返回值类型为函数类型的函数为高阶函数


    /**
     * 高阶函数的调用
     */
//    intArray.forEach(::println)
//    intArray.forEach{
//        print("hello $it")
//    }

    //block执行耗费多少时间
    fun cost(block: () -> Unit) {
        val start = System.currentTimeMillis()
        block()
        println(System.currentTimeMillis() - start)
    }


    //region 折叠
    fun fibonacci(): () -> Long {
        var first = 0L
        var second = 1L
        return {
            val next = first + second
            val current = first
            first = second
            second = next
            current

        }

    }
    //endregion

    cost {
        val fibonacciNext = fibonacci()
        for (i in 0..10) {
            println(fibonacciNext)
        }

    }


}