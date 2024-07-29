package com.yhj.kotlinstudy.chapter_09

import java.lang.IllegalArgumentException
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0
import kotlin.reflect.jvm.isAccessible

/**
 *    @author : 杨虎军
 *    @date   :  2021/05/14
 *    @desc   : 可释放对象引用的不可空类型   （属性代理）
 */
class Kotlin49 {
}

fun <T : Any> releasableNotNull() = ReleasableNotNull<T>()

class ReleasableNotNull<T : Any> : ReadWriteProperty<Any, T> {

    private var value: T? = null

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        this.value = value
    }

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return value ?: throw IllegalArgumentException("not release or released already")
    }

    fun isInitialized() = value != null
    fun release() {
        value = null
    }

}

inline val KProperty0<*>.isInitialized: Boolean
    get() {
        isAccessible = true
        return (this.getDelegate() as? ReleasableNotNull<*>)?.isInitialized()
            ?: throw IllegalArgumentException("Delegate is not instance of ReleasableNotNull or is null")
    }

fun KProperty0<*>.release() {
    isAccessible = true
    (this.getDelegate() as? ReleasableNotNull<*>)?.release()
        ?: throw IllegalArgumentException("Delegate is not instance of ReleasableNotNull or is null")
}

class Bitmap(val width: Int, val height: Int)
class Activity {
    private var bitmap by releasableNotNull<Bitmap>()

    fun onCreate() {
        println(::bitmap.isInitialized)
        bitmap = Bitmap(1920, 1080)
        println(::bitmap.isInitialized)
    }

    fun onDestroy() {
        println(::bitmap.isInitialized)
        ::bitmap.release()
        println(::bitmap.isInitialized)
    }

}


fun main() {
    val activity = Activity()
    activity.onCreate()
    activity.onDestroy()
}