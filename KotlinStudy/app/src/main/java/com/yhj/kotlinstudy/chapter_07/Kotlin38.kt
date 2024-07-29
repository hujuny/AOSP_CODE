package com.yhj.kotlinstudy.chapter_07

/**
 *    @author : 杨虎军
 *    @date   :  2021/05/02
 *    @desc   : 递归整型列表的简单实现
 */
class Kotlin38 {

}

sealed class IntList {
    object Nil : IntList() {
        override fun toString(): String {
            return "Nil"
        }
    }

    class Cons(val head: Int, val tail: IntList) : IntList() {
        override fun toString(): String {
            return "$head,$tail"
        }
    }

    fun joinToString(sep: Char = ','): String {
        return when (this) {
            Nil -> "Nil"
            is Cons -> {
                "${this.head}${sep}${this.tail.joinToString(sep)}"
            }
        }
    }

    fun sum(): Int {
        return when (this) {
            Nil -> 0
            is Cons -> head + tail.sum()
        }

//        return when {
//            Nil.equals("Nil") -> 0
//            this is Cons -> head + tail.sum()
//            else -> 0
//        }
    }

    operator fun component1(): Int? {
        return when (this) {
            Nil -> null
            is Cons -> head
        }
    }

    operator fun component2(): Int? {
        return when (this) {
            Nil -> null
            is Cons -> tail.component1()
        }
    }

    operator fun component3(): Int? {
        return when (this) {
            Nil -> null
            is Cons -> tail.component2()
        }
    }


}


//vararg 变长参数
fun intListOf(vararg ints: Int): IntList {
    return when (ints.size) {
        0 -> IntList.Nil
        else -> {
            IntList.Cons(
                ints[0],
                //使用*将一个一个的参数传递给变长参数ints
                intListOf(*(ints.slice(1 until ints.size).toIntArray()))
            )
        }
    }
}

fun main() {
//    val list = IntList.Cons(0, IntList.Cons(1, IntList.Cons(2, IntList.Cons(3, IntList.Nil))))
    val list = intListOf(0, 1, 2, 3)
    println(list)
    val intListOf = intListOf(0, 1, 2, 3)
    println(intListOf)
    println(intListOf.joinToString('-'))
    println(intListOf.sum())

    //解构
    val (first, second, third) = list
    println(first)
    println(second)
    println(third)
    //最长5个
//    val (a, b) = listOf<Int>()


}
