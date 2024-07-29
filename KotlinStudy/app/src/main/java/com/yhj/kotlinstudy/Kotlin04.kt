package com.yhj.kotlinstudy

import com.yhj.kotlinstudy.chapter_07.Pair

/**
 *    @author : 杨虎军
 *    @date   :  2021/04/10
 *    @desc   : 集合框架
 */

fun main() {

    /**
     * 增加了“不可变 ”集合框架的接口
     * 复用了Java api的所有实现类型
     * 提供了丰富的方法，如foreach/map/flatMap
     * Scala也是一门JVM语言。Kotlin很多特性参考了Scala
     */

    /**
     * 不可变List List<T>
     * 可变List  MutableList<T>
     */
    val intList: List<Int> = listOf(1, 2, 3, 4)
    val any = intList.any { i: Int -> 1 == i }
    println(any)
    val intMutableList: MutableList<Int> = mutableListOf(1, 2, 3, 4)
    intMutableList.add(5)
    println(intMutableList.toString())

    /**
     * 不可变Map Map<K,V>
     * 可变Map  MutableMap<K,V>
     * Any相当于Java的Object
     * to理解为K-V即可
     */
    val anyMap: Map<String, Any> = mapOf<String, Any>("name" to "yhj", "age" to 18)
    //等同于
    val mapOf: Map<String, Any> = mapOf("name" to "yhj", "age" to 18)
    val anyMutableMap = mutableMapOf<String, Any>("name" to "yhj", "age" to 18)


    /**
     * ArrayList包名是kotlin.collections.ArrayList.Java的ArrayList是Java.util.arrayList
     * 包名不一样，但本质一样
     * 通过
     * @Link类型别名
     * 查看源码可知
     * @SinceKotlin("1.1") public actual typealias ArrayList<E> = java.util.ArrayList<E>
     * @SinceKotlin("1.1") public actual typealias HashMap<K, V> = java.util.HashMap<K, V>
     * @SinceKotlin("1.1") public actual typealias HashSet<E> = java.util.HashSet<E>
     */
    val stringList = ArrayList<String>()

    /**
     * 集合框架的读写
     */
    for (i in 0..10) {
        stringList.add("num:$i")
    }
    //java的set/get
    stringList[5] = "hello"
    val hashMap = HashMap<String, Int>()
    hashMap["hello"] = 10
    println(hashMap["hello"])
    /**
     * 集合框架的修改
     */
    for (i in 0..10) {
        //add 便捷写法+=。 可以运算符重载
        stringList += "num:$i"
        println(stringList.toString())
    }

    for (i in 0..10) {
//        stringList.remove("num:$i")
        stringList -= "num:$i"
    }

    /**
     * Pair 两种创建方式 两个元素
     */
    val pair = "hello" to "kotlin"
    val pair1 = Pair("hello", "kotlin")
    val first = pair.first
    val second = pair.second
    val (x, y) = pair
    /**
     * Triple 三个元素
     */
    val triple = Triple("x", 2, 3.0)
    val first1 = triple.first
    val second1 = triple.second
    val third = triple.third
    val (a, b, c) = triple

}
