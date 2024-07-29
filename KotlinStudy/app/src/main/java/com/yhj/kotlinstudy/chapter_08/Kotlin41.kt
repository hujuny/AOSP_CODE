package com.yhj.kotlinstudy.chapter_08

/**
 *    @author : 杨虎军
 *    @date   :  2021/05/13
 *    @desc   :星投影 Projection  使用 * 来代替我们的泛型参数，描述一个未知类型
 *
 */
class Kotlin41 {
    /**
     * 协变点返回泛型参数上限类型
     * 逆变点返回泛型参数下限类型
     */
    class Function2<in P1, in P2> {
        fun invoke(p1: P1, p2: P2) = Unit
    }

    val f: Function2<*, *> = Function2<Number, Any>()
    /**
     * 适用范围
     * '*'不能直接或间接应用在函数或属性上
     * 使用在类型描述的场景
     */

}

fun main() {
    if (Kotlin41().f is Kotlin41.Function2<*, *>) {

    }

    if (Kotlin41().f is Kotlin41.Function2) {
        (Kotlin41().f as Kotlin41.Function2<Number, Any>).invoke(0, "hello")
    }
}