package com.yhj.kotlinstudy.chapter_08

import android.net.Network
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

/**
 *    @author : 杨虎军
 *    @date   :  2021/05/13
 *    @desc   :基于泛型实现model实例的注入
 */
class Kotlin44 {

}


abstract class AbsModel {
    init {
        Models.run { register() }
    }
}

class DataBaseModel : AbsModel() {
    fun query(sql: String): Int = 0
}

class NetworkModel : AbsModel() {
    fun get(url: String): String = """{"code":0}"""
}

class SpModel : AbsModel() {
    init {
        Models.run { register(name = "SpModel") }
    }

    fun select(url: String): String = """{"code":0}"""

}

//object Models {
//    private val modelMap = ConcurrentHashMap<Class<out AbsModel>, AbsModel>()
//
//    fun AbsModel.register() {
//        modelMap[this.javaClass] = this
//        modelMap[this.javaClass] = this
//    }
//
//    fun <T : AbsModel> KClass<T>.get(): T {
//        return modelMap[this.java] as T
//    }
//}

object Models {
    private val modelMap = ConcurrentHashMap<String, AbsModel>()

    fun AbsModel.register(name: String = this.javaClass.simpleName) {
        modelMap[name] = this
    }

    fun <T : AbsModel> String.get(): T {
        return modelMap[this] as T
    }
}

fun initModels() {
    DataBaseModel()
    NetworkModel()
    SpModel()
}

//class ModelDelegate<T : AbsModel>(val kClass: KClass<T>) : ReadOnlyProperty<Any, T> {
//    override fun getValue(thisRef: Any, property: KProperty<*>): T {
//        return Models.run {
//            kClass.get()
//        }
//    }
//}
object ModelDelegate {
    operator fun <T : AbsModel> getValue(thisRef: Any, property: KProperty<*>): T {
        return Models.run {
            property.name.capitalize().get()
        }
    }
}

/**
 * 内联特化 reified
 */
inline fun <reified T : AbsModel> modelOf(): ModelDelegate {
    return ModelDelegate
}

class MainModel {
    //    val dataBaseModel by ModelDelegate(DataBaseModel::class)
//    val networkModel by ModelDelegate(NetworkModel::class)

    //    val dataBaseModel by modelOf<DataBaseModel>()
//    val networkModel by modelOf<NetworkModel>()
    val dataBaseModel: DataBaseModel by ModelDelegate
    val networkModel: NetworkModel by ModelDelegate
    val spModel: SpModel by ModelDelegate
}


fun main() {
    initModels()
    val mainModel = MainModel()
    mainModel.dataBaseModel.query("sql").let(::println)
    mainModel.networkModel.get("http").apply(::println)
    mainModel.spModel.select("mmp").run(::println)

}