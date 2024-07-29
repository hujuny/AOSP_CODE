package com.yhj.kotlinstudy.chapter_10

import java.lang.IllegalArgumentException
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.text.StringBuilder

/**
 *    @author : 杨虎军
 *    @date   : 2021/05/20
 *    @desc   :注解加持反射版Model映射
 */
class Kotlin53 {
}

//默认runtime
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class FieldName(val name: String)

@Target(AnnotationTarget.CLASS)
annotation class MappingStrategy(val kClass: KClass<out NameStrategy>)

interface NameStrategy {
    fun mapTo(name: String): String
}

object UnderScoreToCamel : NameStrategy {
    //实现html_url转换为htmlUrl
    override fun mapTo(name: String): String {
        return name.toCharArray().fold(StringBuilder()) { acc, c ->
            when (acc.lastOrNull()) {
                '-' -> acc[acc.lastIndex] = c.toUpperCase()
                else -> acc.append(c)
            }
            acc
        }.toString()
    }
}

object CamelToUnderScore : NameStrategy {
    //实现htmlUrl转换为html_url
    override fun mapTo(name: String): String {
        return name.toCharArray().fold(StringBuilder()) { acc, c ->
            when {
                c.isUpperCase() -> acc.append('_').append(c.toLowerCase())
                else -> acc.append(c)
            }
            acc
        }.toString()
    }
}

@MappingStrategy(CamelToUnderScore::class)
data class UserVO(
    val login: String,
    //@FieldName("avatar_url")
    val avatarUrl: String,
    var htmlUrl: String
)

data class UserDTO(
    var id: Int,
    var login: String,
    var avatar_url: String,
    var url: String,
    var html_url: String
)

fun main() {
    val userDTO = UserDTO(
        0,
        "Bennyhuo",
        "https://avatars2.githubusercontent.com/u/30511713?v=4",
        "https://api.github.com/users/bennyhuo",
        "https://github.com/bennyhuo"
    )

    val userVO: UserVO = userDTO.mapAs()
    println(userVO)

    val userMap = mapOf(
        "id" to 0,
        "login" to "Bennyhuo",
        "avatar_url" to "https://api.github.com/users/bennyhuo",
        "html_url" to "https://github.com/bennyhuo",
        "url" to "https://api.github.com/users/bennyhuo"
    )

    val userVOFrom: UserVO = userMap.mapAs()
    println(userVOFrom)
}

inline fun <reified From : Any, reified To : Any> From.mapAs(): To {
    return From::class.memberProperties.map { it.name to it.get(this) }
        .toMap().mapAs()
}

inline fun <reified To : Any> Map<String, Any?>.mapAs(): To {
    return To::class.primaryConstructor!!.let {
        it.parameters.map { parameter ->
            parameter to (this[parameter.name]
                ?: (parameter.annotations.filterIsInstance<FieldName>().firstOrNull()?.name?.let(this::get))
                ?: To::class.findAnnotation<MappingStrategy>()?.kClass?.objectInstance?.mapTo(parameter.name!!)?.let(this::get)
                ?: if (parameter.type.isMarkedNullable) null
                else throw IllegalArgumentException("${parameter.name} is required but missing."))
        }.toMap()
            .let(it::callBy)
    }
}