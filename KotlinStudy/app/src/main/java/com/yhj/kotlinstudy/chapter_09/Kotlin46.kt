package com.yhj.kotlinstudy.chapter_09

import java.lang.reflect.ParameterizedType
import kotlin.reflect.full.declaredMemberFunctions

/**
 *    @author : 杨虎军
 *    @date   :  2021/05/13
 *    @desc   : 反射获取泛型实参
 */
class Kotlin46 {
}

interface Api {
    fun getUsers(): List<UserDTO>
}

class UserDTO {}


abstract class SuperType<T> {
    val typeParameter by lazy {
        this::class.supertypes.first().arguments.first().type
    }
    val typeParameterJava by lazy {
        this::class.java.genericSuperclass.safeAs<ParameterizedType>()?.actualTypeArguments?.first()
    }

}

class SubType : SuperType<String>()

fun <T> Any.safeAs(): T? {
    return this as? T


}


fun main() {

    Api::class
        .declaredMemberFunctions
        .first { it.name == "getUsers" }
        .returnType.arguments
        .forEach {
            print(
                it
            )
        }
    (Api::class.java
        .getDeclaredMethod("getUsers")
        .genericReturnType as ParameterizedType)
        .safeAs<ParameterizedType>()
        ?.actualTypeArguments
        ?.forEach {
            print(it)
        }

    val subType = SubType()
    subType.typeParameter.let(::println)
    subType.typeParameterJava.let(::println)


    val json =
        """
                 {"status":0,"msg":"40029","data":"获取微信access_token失败","errorCode":0}
        """;

}