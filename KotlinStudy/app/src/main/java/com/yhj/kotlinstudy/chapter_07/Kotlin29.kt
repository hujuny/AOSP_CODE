package com.yhj.kotlinstudy.chapter_07

import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/**
 *    @author : 杨虎军
 *    @date   :  2021/04/26
 *    @desc   : 代理Delegate
 */
class Kotlin29 {
    /**
     * 接口代理
     * 对象x代替当前类A实现接口B的方法
     */
    /**
     * 属性代理
     * 对象x代替属性a实现getter/setter方法
     */
}

interface MyApi {
    fun a()
    fun b()
}

class MyApiImpl : MyApi {
    override fun a() {

    }

    override fun b() {
    }

}

//class MyApiWrapper(val api: MyApi) : MyApi {
//    override fun a() {
//        api.a()
//    }
//
//    override fun b() {
//        api.b()
//    }
//}

/**
 * 接口代理
 * 对象api代替MyApiWrapper实现接口MyApi
 * 对于对象api的唯一要求就是   实现被代理的接口
 */
class MyApiWrapper(val api: MyApi) : MyApi by api {
    override fun a() {
        println("log")
        api.a()
    }
}

class SuperArray<E>(
    private val list: MutableList<E?> = ArrayList(),
    private val map: MutableMap<String, E> = HashMap()
) : MutableList<E?> by list, MutableMap<String, E> by map {
    override fun isEmpty() = list.isEmpty() && map.isEmpty()
    override val size: Int
        get() = list.size + map.size

    override fun clear() {
        list.clear()
        map.clear()
    }

    override operator fun set(index: Int, element: E?): E? {
        if (list.size <= index) {
            repeat(index - list.size + 1) {
                list.add(null)
            }
        }
        return list.set(index, element)
    }

    override fun toString(): String {
        return """List:[$list];Map:[$map]"""
    }
}

/**
 * 属性代理 lazy
 */
//lazy返回的对象代理了属性的firstName的getter
//接口lazy的实例代理了对象Attribute的实例的属性firstName的getter
class Attribute(val name: String) {
    val firstName by lazy { name.split("")[0] }
}

/**
 * 属性代理 observable
 * observableProperty的实例代理了属性state的getter/setter
 */
class StateManager {
    var state: Int by Delegates.observable(0) { property, oldValue, newValue ->
        println("State changed from $oldValue->$newValue")
    }
}

class Fool {
    val x: Int by X()
    var y: Int by X()

}

class X {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        return 2
    }

    operator fun setValue(fool: Fool, property: KProperty<*>, i: Int) {

    }

}

/**
 * 属性代理 vetoable
 * TODO
 */

fun main() {
    val superArray = SuperArray<String>()
    superArray += "hello"
    superArray["Hello"] = "World"

    superArray[0] = "hello"
    superArray[1] = "world"
    superArray[4] = "!!!"
    println(superArray)

    val stateManager = StateManager()
    stateManager.state = 3
    stateManager.state = 4

}
