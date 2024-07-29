package com.yhj.kotlinstudy.chapter_08

import com.google.gson.Gson

/**
 *    @author : 杨虎军
 *    @date   :  2021/05/13
 *    @desc   :泛型实现原理与内联特化
 */
class Kotlin42 {

    /**
     * 泛型类型无法当做真实类型
     */

    /**
     * 内联特化 reified
     * 如何一个函数是内联函数，
     */
    inline fun <reified T> getMethod(t: T) {
//        val t = T()
        val ts = Array<T>(3) { TODO() }
        val jClass = T::class.java
        val list = ArrayList<T>()
    }

    //应用
    inline fun <reified T> Gson.fromJson(json: String): T = fromJson(json, T::class.java)

}

fun main() {

}