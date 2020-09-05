package com.rollyglobe.network


import com.rollyglobe.network.NetworkConfig.ALL_TIMEOUT
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class AppRetrofitBuilder(private val baseUrl: String, private val interceptor: Interceptor? = null) {

    val cookies = HashSet<String>()

    fun build(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(createClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun createClient(): OkHttpClient {

        val httpLogging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HeaderSettingInterceptor())
            .addInterceptor(httpLogging)
//            .addNetworkInterceptor(StethoInterceptor())
            .addInterceptor(AddCookiesInterceptor())
            .addInterceptor(ReceivedCookiesInterceptor())
            .connectTimeout(ALL_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(ALL_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(ALL_TIMEOUT, TimeUnit.SECONDS)

        interceptor?.let {
            okHttpClient.addInterceptor(it)
        }

        return okHttpClient.build()
    }


    inner class HeaderSettingInterceptor : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {

            val chainRequest = chain.request()

            val request = chainRequest.newBuilder().apply {
                addHeader("Accept", "application/json")
                removeHeader("User-Agent")
                addHeader("User-Agent", "android-retrofit")

            }.build()

            return chain.proceed(request)
        }
    }

    inner class AddCookiesInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val builder = chain.request().newBuilder()

            for (cookie in cookies) {
                builder.addHeader("Cookie", cookie)
            }
            return chain.proceed(builder.build())
        }
    }

    inner class ReceivedCookiesInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val builder = chain.request().newBuilder()
            val originalResponse = chain.proceed(builder.build())

            if (originalResponse.headers("Set-Cookie").isNotEmpty()) {
                for (header in originalResponse.headers("Set-Cookie")) {
                    cookies.add(header)
                }
            }
            return originalResponse
        }
    }
}