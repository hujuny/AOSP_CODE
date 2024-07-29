package com.yhj.kotlinstudy

import kotlin.reflect.KProperty0
import kotlin.reflect.KProperty2

/**
 *    @author : 杨虎军
 *    @date   :  2021/04/11
 *    @desc   : 类和接口
 */

/**
 * 默认public
 */
class Kotlin07
//类内无内容，{}可省略


class SimpleClass {
    //必须初始化
    var x: Int = 0


    //副构造器
    constructor(x: Int) {
        this.x = x
    }

    fun y() {

    }

}

//构造方法简化，等同上面  主构造器
class Simple1Class(
    var x: Int
) {


    fun y() {

    }
}

//: java implement实现
//: AbsClass()  加()是调用构造方法
open class Simple2Class(var x: Int, val y: String) : AbsClass(), SimpleInf {
    /**
     * 接口实现
     */
    override fun absMethod() {}
    override fun simpleMethod() {}
//   final override fun simpleMethod() {}


    override val simpleProperty: Int
        get() {
            return 2
        }

    fun y() {}
}

/**
 * 接口定义
 */
interface SimpleInf {
    fun simpleMethod()

    val simpleProperty: Int
}

/**
 * 抽象类的定义
 */
abstract class AbsClass {
    abstract fun absMethod()

    //加open关键字告诉编译器可以被复写，不加则不能
    open fun overridable() {}
    fun nonOverridable() {}

    /**
     * 默认不可复写
     */

}

/**
 * 加open关键字才可以继承
 */
class Simple3Class(x: Int, y: String) : Simple2Class(x, y) {
    /**
     * 添加final关键字，方法不能被复写
     */
    override fun simpleMethod() {
        super.simpleMethod()
    }
}

fun main() {
    /**
     * 类的实例化
     */
    val simple1Class = Simple1Class(10)
    simple1Class.x

    /**
     * 属性引用
     */
    val kProperty1 = person::age
    kProperty1.get(person(18,"yhj"))
    val per = person(18, "yhj")
    val kProperty2 = per::name
    kProperty2.set("kotlin")
    kProperty2.get()

}


//javaBean
class person(age: Int = 0, name: String) {

    //此处age相当于get+set+private int age
    var age: Int = age //property
        //field相当于 java private int age 当前域
        get() {
            return field
        }
        set(value) {
            field = value
        }
    var name: String = name
        //也是默认的get和set
        get() {
            return field //backing field
        }
        set(value) {
            println()
            field = value
        }


}
