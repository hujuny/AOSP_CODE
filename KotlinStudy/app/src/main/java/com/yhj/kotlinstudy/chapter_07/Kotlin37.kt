package com.yhj.kotlinstudy.chapter_07

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.Gson
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import kotlin.jvm.internal.Intrinsics

/**
 *    @author : 杨虎军
 *    @date   :  2021/05/02
 *    @desc   : 数据类的json序列化
 */
class Kotlin37 {

    /**
     * json框架
     * Gson
     * Moshi
     * Kotlinx.serialization 官方的
     */

}

@ImplicitReflectionSerializer
fun main() {

    val gson = Gson()
    println(gson.toJson(Person1("yhj", 18)))
    println(gson.fromJson("""{"name":"yhj","age":18}""", Person1::class.java))


    val person1 = gson.fromJson("""{"name":"y hj","age":18}""", Person1::class.java)
    //延迟初始化
    //需要配置 gradle invokeInitializers 不配置nullpointerexception
    println(person1.firstName)


    //默认参数值  反序列化fromJson时age=0,不是100
    println(gson.toJson(Person1("yhj")))
    println(gson.fromJson("""{"name":"yhj"}""", Person1::class.java))


    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    val adapter = moshi.adapter(Person1::class.java)
//    println(adapter.toJson(Person1("yhj", 18)))
//    println(adapter.fromJson("""{"name":"yhj","age":18}"""))
    //默认参数值 age反序列化还是100 添加@JsonClass(generateAdapter = true)
    println(adapter.toJson(Person1("yhj")))
    println(adapter.fromJson("""{"name":"y hj"}"""))

    //延迟初始化，不影响
    val person2 = adapter.fromJson("""{"name":"y hj"}""")
    println(person2?.firstName)

    /**
     * @ImplicitReflectionSerializer
     * @Serializable
     */
    println(Json.stringify(Person1("yhj", 18)))
    println(Json.parse<Person1>("""{"name":"yhj","age":18}"""))
    //默认参数值，age还是100，
    println(Json.stringify(Person1("yhj")))
    println(Json.parse<Person1>("""{"name":"yhj"}"""))

    //延迟初始化，不影响
    val person3 = Json.parse<Person1>("""{"name":"yhj"}""")
    println(person3.firstName)


}

//data class Person1(val name: String, val age: Int)
//@Serializable
//data class Person1(val name: String, val age: Int=100)
//@JsonClass(generateAdapter = true)
@PoKo  //noArg 配置
data class Person1(val name: String, val age: Int = 100) {
    val firstName by lazy {
        name.split(" ")[0]
    }
    val lastName = name.split(" ")[1]

}

object LazyExclusionStrategy : ExclusionStrategy {
    override fun shouldSkipField(f: FieldAttributes): Boolean {
        return f.declaredType == Lazy::class.java
    }

    override fun shouldSkipClass(clazz: Class<*>?) = false

}