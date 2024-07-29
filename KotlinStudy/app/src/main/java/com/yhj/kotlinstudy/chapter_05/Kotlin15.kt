package com.yhj.kotlinstudy.chapter_05

/**
 *    @author : 杨虎军
 *    @date   :  2021/04/14
 *    @desc   : lambda表达式
 */
class Kotlin15 {


}

fun main() {

//    fun func(){
//        println("hello")
//    }
    /**
     * 匿名函数
     */
    val func: () -> Unit = fun() {
        println("hello")
    }
    func()
    //匿名函数更具有表现力的写法  lambda表达式

    /**
     * lambda的定义
     */
    val lambda: () -> Unit = {
        print("yhj")
    }

    val f1: (Int) -> Unit = { p: Int ->
        print(p.toString())
    }
    val f2: Function1<Int, Unit> = { p: Int -> println(p) }
    //表达式参数类型从表达式类型推断而来
    val f4: Function1<Int, Unit> = { p -> print(p) }
    //表达式的类型从声明推断而来
    val f5 = { p: Int ->
        println(p)
    }
    //表达式的最后一行为返回值
    val f6 = { p: Int ->
        println(p)
        "hello"
    }

    /**
     * lambda表达式参数省略形式
     */
    val f7: Function1<Int, Unit> = { print(it) }
    f7(10)
}