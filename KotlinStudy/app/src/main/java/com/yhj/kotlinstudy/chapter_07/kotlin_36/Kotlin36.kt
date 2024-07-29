package com.yhj.kotlinstudy.chapter_07.kotlin_36

/**
 *    @author : 杨虎军
 *    @date   :  2021/05/01
 *    @desc   : 内联类  对某一个类型的包装
 */
class Kotlin36 {

    /**
     * java装箱类型的一种类型
     * 编译器会尽可能使用被包装的类型进行优化
     */
}

//必须是val
inline class BoxInt(val value: Int) : Comparable<Int> {
    operator fun inc(): BoxInt {
        return BoxInt(value + 1)
    }

    /**
     * 内联类可以实现接口，但不能继承父类也不能被继承
     */
    override fun compareTo(other: Int) = value.compareTo(other)
}


//inline class UInt
//internal constructor(internal val data: Int):Comparable<UInt>{
//
//}
//内联类模拟枚举  解决java使用枚举类内存开销的问题，优化
inline class State1(val ordinal: Int) {
    companion object {
        var Idle = State1(0)
        var Busy = State1(1)
    }

    fun values() = arrayOf(Idle, Busy)
    val name: String
        get() = ""
}

enum class RouteTypeEnum {
    WALK,
    BUS,
    CAR
}

//fun setRouteType(@RouteTypeDef routeType: Int) {}
fun setRouteType(routeType: RouteTypeInline) {

}

//编译后拆箱为整型，降低内存开销
inline class RouteTypeInline(val value: Int) {
    object RouteTypes {
        val WALK = RouteTypeInline(0)
        val BUS = RouteTypeInline(1)
        val CAR = RouteTypeInline(2)
    }
}

fun main() {
    val boxInt = BoxInt(5)
    if (boxInt < 10) {
        println("value is less than 10")
    }

    //编译优化
    var boxInt1 = BoxInt(5)
    val i = boxInt1.value * 200
    println(i)
    boxInt1++
    println(boxInt1)

    /**
     * Proguard以及Android的R8也会对简单枚举(即不实现接口或没有额外成员的枚举)做优化
     */
    setRouteType(RouteTypeInline(5))
    /**
     * 内联类的限制
     * 主构造器有且仅有一个只读属性
     * 不能定义有backing-field的其他属性
     * 被包装类型不能是泛型类型
     * 不能继承父类也不能被继承
     */
}

