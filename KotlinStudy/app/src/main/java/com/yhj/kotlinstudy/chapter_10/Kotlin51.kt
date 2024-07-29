@file:JvmName("Hello") //标注文件
@file:JvmMultifileClass
package com.yhj.kotlinstudy.chapter_10

import java.io.IOException

/**
 *    @author : 杨虎军
 *    @date   :  2021/05/14
 *    @desc   : 注解 内置注解
 */
class Kotlin51 {
}

@Target(AnnotationTarget.CLASS) //限定作用于类 source binary runtime
@Retention(AnnotationRetention.RUNTIME) //运行时间
annotation class Api(val url:String)  //注解声明   注解参数（支持以下类型及其数组）


@Api("http:")
class GithubApi{

}

@Volatile
var name:String=""


@Throws(IOException::class)
@Synchronized
fun getException(){



}
fun main() {
    /**
     * 注解是对程序的附加信息说明
     * 注解可以对类、函数、函数参数、属性等做标注
     * 注解的信息可用于源码级，编译期，运行时
     * 注解参数（支持以下类型及其数组）
     * 基本类型   KClass     枚举     其它注解
     * 注解(Annotations)  特定语法现象，参与编译
     * 注释(Comments)  存在于源码中，提升可读性
     */

    /**
     * kotlin.annotation.* 用于标注注解的注解
     * kotlin.*标准库的一些通用用途的注解
     * kotlin.jvm.* 用于与java虚拟机交互的注解
     */

    /**
     * 标准库的通用注解
     * Metadata Kotlin反射的信息通过该注解附带在元素上
     * UnsafeVariance 泛型用来破除型变限制
     * Suppress 用来去除编译器警告，警告类型作为参数传入
     */

   getException()
}