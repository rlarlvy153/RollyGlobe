package com.rollyglobe.rollyglobe


import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.IOException
import java.util.concurrent.TimeUnit

object RetrofitCreator {
    val API_BASE_URL = "https://test.rollyglobe.com/"
    private val ALL_TIMEOUT = 1000L

    private var okHttpClient: OkHttpClient
    private var retrofit: Retrofit
    val cookies = HashSet<String>()

    init{
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        val httpLogging = HttpLoggingInterceptor()
        httpLogging.level = HttpLoggingInterceptor.Level.BODY

        okHttpClient = OkHttpClient().newBuilder().apply {

            addInterceptor(httpLogging)
            addInterceptor(HeaderSettingInterceptor())
            addInterceptor(AddCookiesInterceptor())
            addInterceptor(ReceivedCookiesInterceptor())
            connectTimeout(ALL_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(ALL_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(ALL_TIMEOUT, TimeUnit.SECONDS)

        }.build()

        retrofit = Retrofit.Builder().apply{
            baseUrl(API_BASE_URL)
            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create())
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        }.build()

    }
    private  class HeaderSettingInterceptor : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {

            val chainRequest = chain.request()

            val request = chainRequest.newBuilder().apply{
                addHeader("Accept", "application/json")
                removeHeader("User-Agent")
                addHeader("User-Agent", "android-retrofit")

//                addHeader("appKey", SK_API_KEY)
            }.build()

            return chain.proceed(request)
        }
    }
    private class AddCookiesInterceptor : Interceptor{
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val builder = chain.request().newBuilder()

            for(cookie in cookies){
                builder.addHeader("Cookie",cookie)
            }
            return chain.proceed(builder.build())
        }
    }

    private class ReceivedCookiesInterceptor  : Interceptor{
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val builder = chain.request().newBuilder()
            val originalResponse = chain.proceed(builder.build())

            if(!originalResponse.headers("Set-Cookie").isEmpty()){
                for(header in originalResponse.headers("Set-Cookie")){
                    cookies.add(header)
                }
            }
            return originalResponse
        }
    }


    internal fun <T> getRetrofitService(restClass: Class<T>): T {
        return retrofit.create(restClass)
    }

}