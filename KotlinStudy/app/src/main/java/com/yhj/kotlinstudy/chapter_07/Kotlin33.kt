package com.yhj.kotlinstudy.chapter_07

import com.yhj.kotlinstudy.Person
import java.io.Serializable

/**
 *    @author : 杨虎军
 *    @date   :  2021/04/26
 *    @desc   :数据类 data class 不能被继承
 */
class Kotlin33 {
}

//定义在主构造器中的属性又称为component
//编译器基于component自动生成了equals/copy/hashCode/toString
//Component，不可以自定义Getter-Setter
//定义成员最好不可变val
//NoArg插件   AllOpen插件
data class Book(val id: Long, val name: String, val author: Person)

fun main() {
    val book = Book(1L, "", Person(0, "yhj"))
    val id = book.component1()
    val name = book.component2()
    val author = book.component3()

    val pair = "Hello" to "World"
    val (hello, world) = pair


}

data class Pair<out A, out B>(
    public val first: A,
    public val second: B
) : Serializable