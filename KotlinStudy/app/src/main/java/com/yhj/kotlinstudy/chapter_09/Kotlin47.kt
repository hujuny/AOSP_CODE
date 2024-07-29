package com.yhj.kotlinstudy.chapter_09

import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

/**
 *    @author : 杨虎军
 *    @date   :  2021/05/14
 *    @desc   : 为数据类实现DeepCopy
 */
class Kotlin47 {
}

fun <T:Any> T.deepCopy():T{
    if(!this::class.isData) return this

    return this::class.primaryConstructor!!.let { primaryConstructor-> primaryConstructor.parameters.map{parameter->
        val value=(this::class as KClass<T>).memberProperties.first{it.name==parameter.name}.get(this)
        if ((parameter.type.classifier as? KClass<*> )?.isData==true){
            parameter to value?.deepCopy()
        }else{
            parameter to value
        }
    }.toMap().let(primaryConstructor::callBy)
    }
}

fun main() {

}