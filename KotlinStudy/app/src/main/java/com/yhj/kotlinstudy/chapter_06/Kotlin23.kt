package com.yhj.kotlinstudy.chapter_06

import java.io.File

/**
 *    @author : 杨虎军
 *    @date   :  2021/04/18
 *    @desc   : 统计字符个数
 */
class Kotlin23 {
}

fun main() {
    //随便给一个文件，输出除空白字符以外所有字符的个数

    File("build.gradle")
        .readText().toCharArray()
//        .filterNot(Char::isWhitespace)
        .filterNot { it.isWhitespace() }
        .groupBy { it }
        .map { it.key to it.value.size }
        .let { println(it) }
}