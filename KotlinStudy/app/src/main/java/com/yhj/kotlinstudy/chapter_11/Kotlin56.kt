package com.yhj.kotlinstudy.chapter_11

import com.yhj.kotlinstudy.chapter_07.MyApi
import com.yhj.kotlinstudy.chapter_10.*
import retrofit2.Call
import retrofit2.http.POST

/**
 *    @author : 杨虎军
 *    @date   : 2021/06/07
 *    @desc   : 协程的基本概念 它来了
 */
class Kotlin56 {

    fun last(){
    }

}

fun main() {

    /**
     * 协程是可以由程序自行控制挂起、恢复的程序
     * 协程可以用来实现多任务的协作执行
     * 协程可以用来解决异步任务控制流的灵活转移
     *
     * 作用
     * 让异步代码同步化
     * 降低异步程序的设计复杂度
     *
     *
     */

    //多个请求，希望来了
    //协程改造
    //suspend
    //异步逻辑，同步写法

    /**
     * 线程VS协程
     * Thread VS Coroutine
     * 操作系统的线程（内核线程）
     * 语言实现的协程，运行在内核线程之上
     */

    /**
     * 框架级别的支持
     * 官方协程框架
     * Job
     * 调度器
     * 作用域
     * 语言级别的支持
     * Kotlin标准库
     * 协程上下文
     * 拦截器
     * 挂起函数
     */






}

interface MyApi {
    @Get("user")
    suspend fun getUser(@Query("user") user: String):String

//    @POST("/login/{page}")
//    fun getLogin(@Path("page") page:String):Call<String>
}