package com.yhj.kotlinstudy.chapter_08

import java.security.UnrecoverableEntryException

/**
 *    @author : 杨虎军
 *    @date   :  2021/05/13
 *    @desc   :泛型的型变
 */
class Kotlin40 {
    /**
     * 泛型实参的继承关系对泛型类型的继承关系的影响
     * 协变 继承关系一致
     * 逆变 继承关系相反
     * 不变 没有继承关系
     */

    //不变  加out表示协变
    sealed class List<out T> {
        object Nil : List<Nothing>()
        //List<Nothing>不是List<T>的子类，List.Nil也不是List<T>的子类
    }

    //Int ——>Number  List<Int> ——>List<Number>
    /**
     * 协变点
     * 定义协变点：函数返回值为泛型参数
     */
    operator fun <T> List<T>.get(index: Int): T =
        when (index) {
            0 -> throw NoSuchFieldException()
            else -> throw UnrecoverableEntryException()
        }


}

interface Book {

}

interface EduBook : Book
class BookStore<out T : Book> {
    fun getBook(): T {
        TODO()
    }
}

fun covariant() {
    val eduBookStore = BookStore<EduBook>()
    val bookStore: BookStore<Book> = eduBookStore
    val book = bookStore.getBook()
    val eduBook = eduBookStore.getBook()
}

/**
 * 子类派生兼容父类base
 * 生产者Producer<Derived> 兼容Producer<Base>
 * 存在协变点的类的泛型参数必须声明为协变或不变
 * 当泛型类作为泛型参数类实例的生产者时用协变
 */

/**
 * 逆变 in
 * 子类可以替代父类
 * 逆变点：函数参数类型为泛型参数
 */
interface Comparable<in T> {
    operator fun compareTo(other: T): Int
}

/**
 * 逆变点和协变点
 */
interface Function2<in P1, in P2, out R> : Function<R> {
    operator fun invoke(p1: P1, p2: P2): R
}

/**
 * 子类派生兼容父类base
 * 消费者Consumer<Base> 兼容Consumer<Derived>
 * 存在逆变点的类的泛型参数必须声明为逆变或不变
 * 当泛型类作为泛型参数类实例的消费者时用逆变
 */

fun main() {
    Kotlin40.List.Nil
    covariant()
}