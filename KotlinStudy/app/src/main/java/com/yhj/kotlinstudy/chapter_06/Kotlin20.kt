package com.yhj.kotlinstudy.chapter_06

import android.annotation.SuppressLint
import androidx.arch.core.executor.ArchTaskExecutor
import java.io.File

/**
 *    @author : 杨虎军
 *    @date   :  2021/04/17
 *    @desc   : 几个有用的高阶函数
 */
class Kotlin20(var name: String, var age: Int)

@SuppressLint("RestrictedApi")
fun main() {
    val person = Kotlin20("yhj", 18)

    /**
     * 返回表达式结果
     * let和run
     */
    person.let {
    }

    person?.let {}
    val result = with(person, {
        age = 100
        name = "jjj"
    })

    person.run(::println)

    /**
     *返回Receiver
     * also apply
     */
    val also = person.also {
        it.name = "八归少年"
    }
    person.apply {
        name = "XXX"
    }


    /**
     * 自动关闭资源
     * use
     */
    File("build.gradle").inputStream().reader().buffered().use {

        println(it.readLines())
    }

    /**
     * let 适用于处理不为null的操作场景
     * with 适用于调用同一个类的多个方法时，可以省去类名重复，直接调用类的方法即可，经常用于Android中RecyclerView中onBinderViewHolder中，数据model的属性映射到UI上
     * run 适用于let,with函数任何场景。
     *  apply  1、适用于run函数的任何场景，一般用于初始化一个对象实例的时候，操作对象属性，并最终返回这个对象。
    2、动态inflate出一个XML的View的时候需要给View绑定数据也会用到.
    3、一般可用于多个扩展函数链式调用
    4、数据model多层级包裹判空处理的问题
     * also 适用于let函数的任何场景，一般可用于多个扩展函数链式调用
     */


}