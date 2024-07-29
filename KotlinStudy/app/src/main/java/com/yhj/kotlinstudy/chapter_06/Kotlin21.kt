package com.yhj.kotlinstudy.chapter_06

import android.os.Build
import androidx.annotation.RequiresApi
import java.lang.StringBuilder

/**
 *    @author : 杨虎军
 *    @date   :  2021/04/17
 *    @desc   : 集合变换和序列
 */
class Kotlin21 {

}

@RequiresApi(Build.VERSION_CODES.N)
fun main() {


    /**
     * forEach函数不能continue或break
     */
    val list: ArrayList<Int> = arrayListOf(1, 2, 3, 4)
    list.forEach {
        if (it == 2) return@forEach
    }

    /**
     * filter操作 过滤元素
     * 变换序列
     */
    //[1,2,3,4]   e%2=0       得到2，4 组成新集合[2,4]
    list.filter { it % 2 == 0 }
    //sequence 转成序列，转换为懒序列
    list.asSequence().filter { it % 2 == 0 }

    /**
     * map 映射
     */
    //[1,2,3,4]   y=x*2+1  得到3,5,7,9 组成新集合[3,5,7,9]
    list.map { it * 2 + 1 }
    list.asSequence().map { it * 2 + 1 }

    //懒序列加载，可查看日志了解加载过程 懒汉式
    list.asSequence()
        .filter {
            println("filter$it")
            it % 2 == 0
        }
        .map {
            println("map$it")
            it * 2 + 1
        }
        .forEach {
            println("forEach$it")
        }

    //注意：懒加载如果没有foreach，就会堵塞，不会输出，只有在需要的时候

    println("-".repeat(20))

    //饿汉式加载
    list.filter {
        println("filter$it")
        it % 2 == 0
    }
        .map {
            println("map$it")
            it * 2 + 1
        }
        .forEach {
            println("forEach$it")
        }

    /**
     * flatMap
     * 由集合的一个元素映射成一个集合
     */
    //[1,2,3] 映射 1 [0] 2 [0,1]  3[0,1,2]    IntArray(e){it}    [0,0,1,0,1,2]
    list.flatMap { 0 until it }.joinToString().let(::println)

    list.asSequence().flatMap { 0 until it }.asSequence().joinToString().run(::println)

    /**
     * 聚合操作
     */

    /**
     * sum 所有元素求和
     */
    /**
     * reduce 将元素依次按照规则聚合，结果与元素类型一致
     */
    /**
     * fold  给定初始值，将元素按照规则聚合，结果与初始值类型一致
     */
    //[1,2,3] 初始值""  "1"  "12" "123"  最终"123"
    //leftFold 1,2,3   rightFold 3,2,1
    list.fold(StringBuilder()) { acc, i -> acc.append(i) }

    /**
     * 思考：Rxjava中的变换操作符懒加载情况是咋样的
     * zip变换
     */
    //[1,2,3]  经过[x,y]    得到[1x,1y],[2x,2y],[3x,3y]  最后[1x,1y,2x,2y,3x,3y]
}