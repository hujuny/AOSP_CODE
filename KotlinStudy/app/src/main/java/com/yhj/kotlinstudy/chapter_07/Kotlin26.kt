package com.yhj.kotlinstudy.chapter_07

/**
 *    @author : 杨虎军
 *    @date   :  2021/04/21
 *    @desc   :类的构造器
 */

/**
 * 继承父类，同时调用了父类的构造器
 */
class Kotlin26(var age: Int, var name: String) : Animal() {

    //第一个参数类内全局可见，第二个参数构造器内可见(init块，属性初始化)
//    class Kotlin26 constructor(var age: Int, name: String) {
//        //init块类似于主构造器的方法体，init块有多个，最终会合并执行
//        var name: String
//
//        init {
//            this.name = name
//        }
//    }
//    等同
//    class Kotlin26 (var age: Int)


    /**
     * 副构造器
     * 定义了主构造器后在类内部再定义的构造器都被称为副构造器
     */
    constructor(age: Int) : this(age, "unknown") {}

    /**
     * 不定义主构造器(报错)
     */
//    class Kotlin26 : Animal() {
//
//        var age: Int
//        var name: String
//
//        constructor(age: Int, name: String) {
//            this.age = age
//            this.name = name
//        }
//
//        constructor(age: Int) : this(age, "unknown")
//
//    }

    /**
     * 主构造器+默认参数(推荐)
     */
    class Main(var age: Int, var name: String = "unknown") : Animal() {
    }

    /**
     * 在java中使用，且重载能被看到，添加JvmOverloads注解
     * 主构造器默认参数在java代码中可以以重载的方式调用
     */
    class a
    @JvmOverloads
    constructor(var age: Int, var name: String = "unknown") : Animal() {}

    /**
     * 构造同名的工厂函数
     */
    class b(var age: Int, var name: String = "unknown") {

        override fun equals(other: Any?) = (other as? b)?.name?.equals(name) ?: false

        override fun hashCode() = name.hashCode()
    }

    val c = HashMap<String, b>()
    fun b(name: String): b {
        return c[name] ?: b(1, name).also {
            c[name] = it
        }
    }


}

fun main() {
    //不一样
    val str = String()
    val str1 = String(charArrayOf('1', '2'))
}


abstract class Animal {
}