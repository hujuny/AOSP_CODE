package com.yhj.kotlinstudy.chapter_11

import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicReference
import kotlin.IllegalStateException
import kotlin.coroutines.*
import kotlin.coroutines.ContinuationInterceptor

/**
 *    @author : 杨虎军
 *    @date   : 2021/06/09
 *    @desc   : 仿Lua协程实现非对称协程
 */
class Kotlin60 {
}


sealed class Status {
    class Created(val continuation: Continuation<Unit>) : Status()
    class Yielded<P>(val continuation: Continuation<P>) : Status()
    class Resumed<R>(val continuation: Continuation<R>) : Status()
    object Dead : Status()
}

class Coroutine<P, R>(
    override val context: CoroutineContext = EmptyCoroutineContext,
    private val block: suspend Coroutine<P, R>.CoroutineBody.(P) -> R

) : Continuation<R> {

    companion object {
        fun <P, R> create(
            context: CoroutineContext = EmptyCoroutineContext,
            block: suspend Coroutine<P, R>.CoroutineBody.(P) -> R
        ): Coroutine<P, R> {
            return Coroutine(context, block)
        }
    }


    override fun resumeWith(result: Result<R>) {
        val previousStatus = status.getAndUpdate {
            when (it) {
                is Status.Created -> throw IllegalStateException("Never started!")
                is Status.Yielded<*> -> throw IllegalStateException("Already yielded")
                is Status.Resumed<*> -> {
                    Status.Dead
                }
                Status.Dead -> throw java.lang.IllegalStateException("Already dead!")
            }
        }
        (previousStatus as? Status.Resumed<R>)?.continuation?.resumeWith(result)
    }

    suspend fun resume(value: P): R = suspendCoroutine { continuation ->
        val previousStatus = status.getAndUpdate {
            when (it) {
                is Status.Created -> {
                    body.parameter = value
                    Status.Resumed(continuation)
                }
                is Status.Yielded<*> -> {
                    Status.Resumed(continuation)
                }
                is Status.Resumed<*> -> throw IllegalStateException("Already resumed!")
                Status.Dead -> throw IllegalStateException("Already dead!")
            }
        }
        when (previousStatus) {
            is Status.Created -> previousStatus.continuation.resume(Unit)
            is Status.Yielded<*> -> (previousStatus as Status.Yielded<P>).continuation.resume(value)

        }
    }

    inner class CoroutineBody {
        var parameter: P? = null
        suspend fun yield(value: R): P = suspendCoroutine { continuation ->
            val previousStatus = status.getAndUpdate {
                when (it) {
                    is Status.Created -> throw IllegalStateException("Already created!")
                    is Status.Yielded<*> -> throw IllegalStateException("Already resumed!")
                    is Status.Resumed<*> -> Status.Yielded(continuation)
                    Status.Dead -> throw IllegalStateException("Already dead!")
                }
            }
            (previousStatus as? Status.Resumed<R>)?.continuation?.resume(value)
        }
    }

    private val body = CoroutineBody()
    private val status: AtomicReference<Status>

    val isActive: Boolean
        get() = status.get() != Status.Dead

    init {
        val coroutineBlock: suspend CoroutineBody.() -> R = { block(parameter!!) }
        val start = coroutineBlock.createCoroutine(body, this)
        status = AtomicReference(Status.Created(start))
    }
}

class Dispatcher : ContinuationInterceptor {
    override val key = ContinuationInterceptor


    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
        return DispatcherContinuation(continuation,)
    }
}

class DispatcherContinuation<T>(
     val continuation: Continuation<T>
) : Continuation<T> by continuation {

    override val context: CoroutineContext
        get() = continuation.context

    private val executor = Executors.newSingleThreadExecutor()

    override fun resumeWith(result: Result<T>) {
        executor.submit {
            continuation.resumeWith(result)
        }
    }

}


suspend fun main() {

    val producer = Coroutine.create<Unit, Int>(Dispatcher()) {
        for (i in 0..3) {
            println("Send $i")
            yield(i)
        }
        200
    }

    val consumer =
        Coroutine.create<Int, Unit>(Dispatcher()) { param: Int ->
            println("start$param")
            for (i in 0..3) {
                val value = yield(Unit)
                println("receive $value")
            }
        }

    while (producer.isActive && consumer.isActive) {
        val result = producer.resume(Unit)
        consumer.resume(result)
    }
}