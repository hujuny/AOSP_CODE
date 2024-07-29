package com.yhj.kotlinstudy

import java.util.*

/**
 *    @author : 杨虎军
 *    @date   :  2021/04/15
 *    @desc   : 内联函数
 *    通过反编译代码理解原理
 */

/**
 * 定义内联函数
 */
inline fun hello() {
    println("hello")
}


/**
 * 高阶函数内联
 * 1.函数本身被内联到调用处
 * 2.函数的函数参数被内联到调用处
 */

/**
 * 高阶函数与内联更配
 */
inline fun cost(block: () -> Unit) {
    val start = System.currentTimeMillis()
    block()
    println(System.currentTimeMillis() - start)
}

fun main() {

    cost {
        print("hello")
    }
    val ints = intArrayOf(1, 2, 3, 4)
    ints.forEach {
        print("hello$it")
    }

    /**
     * 内联函数的return
     * @forEach kotlin标签
     */
    val ints1 = intArrayOf(1, 2, 3, 4)
    ints1.forEach {
        if (it == 3) return@forEach //跳出这一次的内联函数调用，等价continue
    }

    for (element in ints) {
        println("hello$element")
    }

    /**
     * non local return
     */

    nonLocalReturn {
        return //从外部函数返回
    }

    /**
     * 内联函数的限制
     * public/protected的内联方法只能访问对应类的public成员
     * 内联函数的内联函数参数不能被存储(赋值给变量)
     * 内联函数的内联函数参数只能传递给其它内联函数
     */

}


inline fun nonLocalReturn(block: () -> Unit) {
    block()
}

//inline fun Runnable(block: () -> Unit): Runnable {
//    return object : Runnable {
//        override fun run() {
//            block()  //有可能存在不合法的 non local return；因为block的调用处与定义处不在同一个调用上下文
//        }
//
//    }
//}

//解决办法
//inline fun Runnable(crossinline block: () -> Unit): Runnable {
//    return object : Runnable {
//        override fun run() {
//            block()
//        }
//
//    }
//}

//noinline 禁止函数参数被内联，推荐使用crossinline
inline fun Runnable(noinline block: () -> Unit): Runnable {
    return object : Runnable {
        override fun run() {
            block()
        }

    }
}

/**
 * 内联属性
 * 没有backing-field的属性的getter/setter可以被内联
 *
 */
var pocket:Double=0.0
var money:Double

    inline get()=pocket
    inline set(value){
        pocket=value
    }