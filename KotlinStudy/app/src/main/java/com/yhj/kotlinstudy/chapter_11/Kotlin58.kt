package com.yhj.kotlinstudy.chapter_11

import android.content.BroadcastReceiver
import android.util.Log
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 *    @author : 杨虎军
 *    @date   : 2021/06/08
 *    @desc   : Kotlin协程的基本要素
 */
class Kotlin58 {
}

fun main() {

    /**
     * 挂起函数的调用处被称为挂起点
     * 以suspend修饰的函数
     * 挂起函数只能在其它挂起函数或协程中使用
     */

    /**
     * 真正的挂起必须异步调用resume，包括：
     * 1.切换到其它线程resume
     * 2.单线程事件循环异步执行
     * 在suspendCoroutine中直接调用resume也算没有挂起
     */
    /**
     * 将回调转写成挂起函数
     * 1.使用suspendCoroutine获取挂起函数的Continuation
     * 回调成功的分支使用Continuation.resume(value)
     * 回调失败的分支使用Continuation.resumeWithException(e)
     */

    /**
     * suspend函数本身执行需要一个Continuation实例在恢复时调用，即此处的参数：completion
     * 返回值Continuation<Unit>则是创建出来的协程的载体，receiver suspend函数会被传给该实例作为协程的实际执行体
     */

    /**
     * 协程上下文
     * 协程执行过程中需要携带数据
     * 索引是CoroutineContext.Key
     * 元素是CoroutineContext.Element
     */
    /**
     * 拦截器ContinuationInterceptor是一类协程上下文元素
     * 可以对协程上下文所在协程的Continuation进行拦截
     */
    /**
     * SafeContinuation的作用：
     * 1.resume只被调用一次
     * 2.如果在当前线程调用栈上直接调用则不会挂起
     */
    /**
     * 1.SafeContinuation仅在挂起点实现
     * 2.拦截器在每次（恢复）执行协程体时调用
     * 3.SuspendLambda是协程函数体
     */

    /**
     * 协程体内的代码都是通过Continuation.resumeWith调用
     * 每调用一次label+1，每一个挂起点对应一个case分支
     * 挂起函数在返回COROUTINE_SUSPENDED时才会挂起
     */


    suspend {

    }.createCoroutine(object : Continuation<Unit> {
        override val context = EmptyCoroutineContext

        override fun resumeWith(result: Result<Unit>) {
            Log.e("yhj", "Coroutine End with$result")
        }

    }).resume(Unit)


}

private fun Unit.resume(unit: Unit) {
    TODO("Not yet implemented")
}

suspend fun foo() {}

fun foo1(continuation: Continuation<Unit>) {}

fun bar(a: Int, continuation: Continuation<String>): Any {
    return "hello"
}

fun <T> (suspend () -> T).createCoroutine(
    completion: Continuation<T>
) {
}

fun <R, T> (suspend R.() -> T).startCoroutine(
    receiver: R,
    completion: Continuation<T>
) {
}

interface ContinuationInterceptor : CoroutineContext.Element {
    fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T>
}



