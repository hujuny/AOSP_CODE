package com.yhj.kotlinstudy.chapter_09

import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.typeOf

/**
 *    @author : 杨虎军
 *    @date   :  2021/05/13
 *    @desc   :反射  允许程序在运行时访问程序结构的一类特性
 */
class Kotlin45 {

}

object B{
    fun hello(){

    }
}

class A{
    fun String.hello(){

    }
}
@ExperimentalStdlibApi
fun main() {

    //添加依赖
    //支持访问kotlin所有属性
    /**
     * 缺点：引入kotlin反射库，第一次调用慢
     */

    val kCls = String::class
    kCls.java.kotlin
    kCls.declaredMemberProperties.firstOrNull()
    B::class.objectInstance?.hello()
    val mathMap=typeOf<Map<String,Int>>()
    mathMap.arguments.forEach {
        print(it)
    }

}