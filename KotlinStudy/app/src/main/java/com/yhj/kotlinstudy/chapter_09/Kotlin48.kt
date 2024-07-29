package com.yhj.kotlinstudy.chapter_09

import java.lang.IllegalArgumentException
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

/**
 *    @author : 杨虎军
 *    @date   :  2021/05/14
 *    @desc   : Model隐射
 */
class Kotlin48 {

}

data class UserVO(val login: String, var avatarUrl: String)

data class UsersDTO(
    var id: Int,
    var login: String,
    var avatar_url: String,
    var url: String,
    var html_url: String
)

inline fun <reified From : Any, reified To : Any> From.mapAs(): To {
    return From::class.memberProperties.map { it.name to it.get(this) }.toMap().mapAs()
}

inline fun <reified To : Any> Map<String, Any?>.mapAs(): To {
    return To::class.primaryConstructor!!.let {
        it.parameters.map { parameter ->
            parameter to (this[parameter.name] ?: if (parameter.type.isMarkedNullable) null
            else throw IllegalArgumentException())
        }.toMap().let(it::callBy)
    }
}

fun main() {

    val usersDTO = UsersDTO(0, "yhj", "https", "http", "http://")
    val userVO: UserVO = usersDTO.mapAs()
    println(userVO)

}