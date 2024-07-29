package com.yhj.kotlinstudy.chapter_08

/**
 *    @author : 杨虎军
 *    @date   :  2021/05/05
 *    @desc   : 泛型的基本概念
 */
class Kotlin39 {

    inline fun <T> max1(a: T, b: T): T {
        return b
    }

    fun <T : Comparable<T>> maxOf(a: T, b: T): T {
        return if (a > b) a else b
    }

    /**
     * 多个约束
     */
    fun <T> callMax(a: T, b: T)
            where T : Comparable<T>, T : () -> Unit {
        if (a > b) a() else b()
    }

    /**
     * 多个泛型参数
     */
    fun <T, R> callMax(a: T, b: T): R
            where T : Comparable<T>, T : () -> R, R : Number {
        return if (a > b) a() else b()
    }
}

fun main() {
    /**
     * 类型层面的抽象
     * 通过泛型参数构造更加通用的类型的能力
     * 让符合继承关系的类型批量实现某些能力
     */

    val max = maxOf(1, 2)
    val max1 = Kotlin39().max1<String>("", "")
    /**
     * 泛型的约束
     */


}