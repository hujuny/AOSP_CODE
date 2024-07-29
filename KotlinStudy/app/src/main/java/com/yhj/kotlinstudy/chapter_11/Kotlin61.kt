package com.yhj.kotlinstudy.chapter_11

import kotlin.coroutines.Continuation

/**
 *    @author : 杨虎军
 *    @date   : 2021/06/09
 *    @desc   :揭秘 suspend fun main
 */
class Kotlin61 {


}

//可挂起的main函数
//suspend fun main() {

//}

fun main1(continuation: Continuation<Unit>):Any?{
    return ""
}

fun main(){
    runSuspend(::main1 as suspend()->Unit)
}

fun runSuspend(suspendFunction0: suspend () -> Unit) {
    TODO("Not yet implemented")
}

