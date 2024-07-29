package com.yhj.kotlinstudy

/**
 *    @author : 杨虎军
 *    @date   :  2021/04/09
 *    @desc   : 数组
 */
fun main() {

    /**
     * 数组
     * IntArray java的int[]
     * Array<Int> 整型装箱，类似java Integer[]
     * CharArray
     * Array<Char>
     * Array<String>
     */
    val a = intArrayOf(1, 2, 3, 4, 5)
    //it的值为数组下标，表示值为下标+1，it*3 it/2都可以
    val b = IntArray(5) { it + 1 }
//    打印数组，看源码就知道😂
    println(a.contentToString())
    println(b.get(3))
    val array = Array<Int>(5) { i: Int -> i + 1 }
    /**
     * 数组长度
     */
    println(a.size)
    /**
     * 数组读写
     */
    val c = arrayOf("hello", "world", "kotlin")
    arrayOf(1, 2, 3)
    c[1] = "yhj"
    println("${c[1]},${c[2]}")
    /**
     * 数组遍历
     */
    val d = floatArrayOf(1f, 2f, 3f, 4f)
    for (element in d) {
        println(element)
    }

    //默认值就是int，可以省略
    d.forEach { element -> println(element) }
    d.forEach { println(it) }
    //过时，使用any{element==1f}
    if (1f in d) {
        println(true)
    }
    val any = d.any { i: Float -> 1f == i }
    println(any)


}