package com.yhj.kotlinstudy

import android.widget.Toast
import okhttp3.OkHttpClient
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.File

/**
 *    @author : 杨虎军
 *    @date   :  2021/04/12
 *    @desc   : Retrofit实现
 */
class RetrofitClient {
    /**
     * Kotlin 单例
     * Api
     */

    private var retrofit: Retrofit

    private val BASE_URL: String = "https://www.wanandroid.com/"

    object list {
        //用object修饰，相当于Java中的static，用object修饰一个变量，可以实现全局变量的效果
        var list: ArrayList<String>? = null
    }

    //用companion object包裹方法，实现java中static方法的效果
    companion object {
        private var instance: RetrofitClient? = null
            get() {
                if (field == null) {
                    field = RetrofitClient()
                }
                return field
            }

        @Synchronized
        fun get(): RetrofitClient {
            return instance!!
        }
    }

    private fun getClient(): OkHttpClient {

        return OkHttpClient.Builder().build()

    }


    fun getApi(): Api {
        //kotlin 拿到java的class
        return retrofit.create(Api::class.java)
    }

    init {
        retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getClient())
            .build()
    }


}

interface Api {


    @GET("wxarticle/list/408/{page}/json")
    fun getRepository(@Path("page") page: Int, @Query("name") name: String): Call<String>

}

fun main() {
    RetrofitClient().getApi().getRepository(0, "yhj").execute()

    RetrofitClient.get().getApi().getRepository(0, "yhj").enqueue(object : Callback<String> {
        override fun onResponse(call: Call<String>, response: Response<String>) {
        }

        override fun onFailure(call: Call<String>, t: Throwable) {
        }

    })

    File("").writeText("123456")
    File("").readLines()
    File("").readText()
}
