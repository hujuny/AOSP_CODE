package com.yhj.kotlinstudy.chapter_10

import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.StringBuilder
import java.lang.reflect.Proxy
import java.security.acl.Owner
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.functions
import kotlin.reflect.full.valueParameters

/**
 *    @author : 杨虎军
 *    @date   : 2021/05/17
 *    @desc   : 仿Retrofit反射读取注解请求网络
 */
class Kotlin52 {
}

data class User(var login: String, var location: String, var bio: String)

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class api(val url: String)

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.VALUE_PARAMETER)
annotation class Path(val url: String = "")

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Get(val url: String = "")

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class PathVariable(val url: String = "")

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class Query(val url: String = "")

@api("http://www.yanghjun.com")
interface GitApi {

    @api("users")
    interface user {

        @Get("{name}")
        fun get(name: String): User

        @Get("{name}/flowers")
        fun followers(name: String): List<user>
    }

//    @Api("repos")
    interface Repos {
        @Get("{owner}/{repo}/forks")
        fun forks(owner: String, repo: String)
    }
}

object RetrofitApi {
    const val PATH_PATTERN = """(\{(\w+)\})"""
    val okHttp = OkHttpClient()
    val gson = Gson()

    val enclosing = { cls: Class<*> ->
        var currentCls: Class<*>? = cls
        sequence {
            while (currentCls != null) {
//                yield(currentCls?.enclosingClass)
//                currentCls=currentCls?.enclosingClass

                currentCls = currentCls?.also { yield(it) }?.enclosingClass
            }
        }
    }

//    inline fun <reified T> create(): T {
//        val functionMap = T::class.functions.map { it.name to it }.toMap()
//        val interfaces = enclosing(T::class.java).takeWhile { it.isInterface }.toList()
//
//        //倒过来遍历，从外到里
//        val apiPath = interfaces.foldRight(StringBuilder()) { clazz, acc ->
//            acc.append(clazz.getAnnotation(Api::class.java)?.url?.takeIf { it.isNotEmpty() }
//                ?: clazz.name).append("/")
//        }.toString()
//        return Proxy.newProxyInstance(
//            RetrofitApi.javaClass.classLoader,
//            arrayOf(T::class.java)
//        ) { proxy, method, args ->
//            functionMap[method.name]?.takeIf { it.isAbstract }?.let { kFunction ->
//                val parameterMap = kFunction.valueParameters.map {
//                    //valueParameters中包含receiver,需要index-1对应args
//                    it.name to args[it.index - 1]
//                }.toMap()
//                val endPoint = kFunction.findAnnotation<Get>()!!.url.takeIf { it.isNotEmpty() }
//                    ?: kFunction.name
//                val compileEndPoint = Regex(PATH_PATTERN).findAll(endPoint).map { matchResult ->
//                    matchResult.groups[1]!!.range to parameterMap[matchResult.groups[2]!!.value]
//                }.fold(endPoint) { acc, pair ->
//                    acc.replaceRange(pair.first, pair.second.toString())
//                }
//                val url:String = apiPath + compileEndPoint
//                println(url)
//                okHttp.newCall(Request.Builder().url(url).get().build()).execute().body?.charStream()?.use {
//                    gson.fromJson(JsonReader(it),method.genericReturnType)
//                }
//            }
//
//        } as T
//    }

}

fun main() {
//    val userApi = RetrofitApi.create<GitApi.user>()
//    println(userApi.get("八归少年"))

}