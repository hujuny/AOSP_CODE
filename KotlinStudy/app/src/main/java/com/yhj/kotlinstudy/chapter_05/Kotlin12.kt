package com.yhj.kotlinstudy.chapter_05

import com.yhj.kotlinstudy.Person

/**
 *    @author : 杨虎军
 *    @date   :  2021/04/13
 *    @desc   : 常量和变量
 */


fun main() {
    /**
     * 变量
     */
    var a = 2
    a = 3
    /**
     * 只读变量
     */
    val b = 3

    /**
     * 常量引用
     *
     */
    val person = Person(18, "yhj")//堆上创建对象
    person.age = 19//对象改变但引用没变
    /**
     * 编译期和运行时常量
     */
    //编译时即可确定常量的值，并用值替换调用处
//    const val e = 5
    //运行时才能确定的值，调用处通过引用获取值
    val f: Int



}

/**
 * 常量
 * const
 * 只能定义在全部范围
 * 只能修饰基本类型
 * 必须立即用字面量初始化
 */
const val d = 10


class Kotlin12 {


    val c: Int
        get() {
            return (Math.random() * 100).toInt()
        }

}