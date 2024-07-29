package com.yhj.kotlinstudy

import com.yhj.kotlinstudy.chapter_06.kotlin_22.EventManager
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 *    @author : 杨虎军
 *    @date   :  2021/04/17
 *    @desc   : SAM转换  Single Abstract Method 函数式接口
 */
class Kotlin22 {

}

fun main(){
    /**
     * java 的SAM转换
     */
    //java中的lambda表达式没有自己类型，必须有一个接口接收，且只有一个方法

    //匿名内部类
//    executor.submit(object :Runnable{
//        override fun run() {
//            println("run in executor")
//        }
//    })
    //SAM
    //()->Unit类型的lambda转换为Runnable
//    executor.submit(println("run in executor"))

    /**
     * 一个参数类型只有一个方法的java接口的java方法
     * 调用时可用Lambda表达式做转换作为参数
     */

    val executor:ExecutorService = Executors.newSingleThreadExecutor()
    executor.submit(object :Runnable{
        override fun run() {
            println("run in executor")
        }

    })

    executor.submit(Runnable { println("run in executor")})

    executor.submit { println("run in executor") }

    submitRunnable{println("hello")}

    //推荐做法
    submit {  }

    val eventManager = EventManager()

    val onEvent=object: EventManager.OnEventListener{
        override fun onEvent(event: Int) {
            println("onEvent$event")
        }

    }
    eventManager.addOnEventListener(onEvent)
    eventManager.removeOnEventListener(onEvent)


}
fun Runnable1(block:()->Unit):Runnable{
    return object :Runnable{
        override fun run() {
            block()
        }

    }

}
fun submitRunnable(runnable: Runnable){
    runnable.run()
}
fun submit(invokable: Invokable){
    invokable.invoke()
}

typealias FunctionX=()->Unit
fun submit(lambda:FunctionX){

}
interface Invokable{
    fun invoke()

}