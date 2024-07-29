package com.yhj.kotlinstudy.chapter_07

import java.io.File
import java.io.FileInputStream
import java.net.URL
import java.util.*
import kotlin.Exception
import kotlin.reflect.KProperty

/**
 *    @author : 杨虎军
 *    @date   :  2021/04/26
 *    @desc   : 使用属性代理读写Properties
 */
class Kotlin30 {

}

/**
 * TODO
 * 属性代理读写SharedPreference
 */

class PropertiesDelegate(private val path: String, private val defaultValue: String = "") {

    private lateinit var url: URL
    private val properties: Properties by lazy {
        val prop = Properties()
        url = try {
            javaClass.getResourceAsStream(path).use {
                prop.load(it)
            }
            javaClass.getResource(path)!!
        } catch (e: Exception) {
            try {
                ClassLoader.getSystemClassLoader().getResourceAsStream(path).use {
                    prop.load(it)
                }
                ClassLoader.getSystemClassLoader().getResource(path)!!
            } catch (e: Exception) {
                FileInputStream(path).use {
                    prop.load(it)
                }
                URL("file://${File(path).canonicalPath}")
            }
        }
        prop
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return properties.getProperty(property.name, defaultValue)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        properties.setProperty(property.name, value)
        File(url.toURI()).outputStream().use {
            properties.store(it, "hello")
        }
    }
}

abstract class AbsProperties(path: String) {
    protected val prop = PropertiesDelegate(path)

}

class Config : AbsProperties("Config.properties") {
    var author by prop
    var version by prop
    var desc by prop
}

fun main() {
    val config = Config()
    println(config.author)
//    config.author="2.0.0"
//    println(config.author)
}

