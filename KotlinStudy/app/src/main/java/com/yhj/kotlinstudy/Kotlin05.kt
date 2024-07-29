package com.yhj.kotlinstudy

import java.util.function.Function

/**
 *    @author : 杨虎军
 *    @date   :  2021/04/10
 *    @desc   :函数
 */
fun main() {
//    //Foo就是bar方法的receiver
    val foo: Foo = Foo()
    val bar: Any = foo.bar("", 1L)

    lengthen("10")

}


/**
 * Unit相当于java中的void类型，可省略
 */
fun function(args: Array<String>): Unit {
    println(args.contentToString())

    /**
     * 形式上，有receiver的函数即为方法
     */
}

fun foo() {
    //返回类型Unit
}

fun foo1(p0: Int): String {
    //返回类型为String
    return ""
}

class Foo {
    fun bar(p0: String, p1: Long): Any {
        //返回类型为Any
        return ""
    }
}

/**
 * 函数的引用,用于函数传递
 */
fun foo2() {
    val kFunction0: () -> Unit = ::foo
    //可以省略
    val kFunction1 = ::foo

    val kFunction3: (Foo, String, Long) -> Any = Foo::bar
    val kFunction4: Foo.(String, Long) -> Any = Foo::bar
    //最后一个参数为返回值类型
    val kFunction5: Function3<Foo, String, Long, Any> = Foo::bar

    val foo = Foo()
    //绑定receiver的函数引用
    val kFunction2: (String, Long) -> Any = foo::bar

    yy(kFunction3)

}

fun yy(kFunction3: (Foo, String, Long) -> Any) {
    kFunction3(Foo(), "hello", 3L)
    //等同
    kFunction3.invoke(Foo(), "hello", 3L)
}

/**
 * 变长参数 vararg
 */
fun lengthen(vararg args: String) {

    println(args.contentToString())

//    val multiReturnValues:Triple<Int,Long,Double> = multiReturnValues()
    //解构写法
    val (a, b, c) = multiReturnValues()//伪多返回值
    //int+long=long
    val l = a + b
    //int+double=double
    val d = a + c

    defaultParameter(0)
    defaultParameter1(y = "12345", 10)
}

/**
 * 多返回值
 */
fun multiReturnValues(): Triple<Int, Long, Double> {

    return Triple(1, 2L, 3.0)
}

/**
 * 默认参数
 */
fun defaultParameter(x: Int, y: String = "yhj", z: Long = 0L) {

}

/**
 * 具名参数
 */
fun defaultParameter1(y: String = "yhj", x: Int, z: Long = 0L) {
    //当调用此函数时候，编译器无法判断传递值给那个参数
    //y为声明参数名，显式接收函数
//    defaultParameter1(y="12345",10)

}
