package com.rollyglobe.network


import com.rollyglobe.BuildConfig
import com.rollyglobe.network.NetworkConfig.ALL_TIMEOUT
import com.rollyglobe.network.NetworkConfig.API_BASE_URL
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

class AppRetrofitBuilder(private val baseUrl: String, private val interceptor: Interceptor? = null) {


    private var okHttpClient: OkHttpClient
    private var retrofit: Retrofit
    val cookies = HashSet<String>()

    fun build() : Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(createClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun createClient(): OkHttpClient {

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HeaderSettingInterceptor())
//            .addNetworkInterceptor(StethoInterceptor())
            .addInterceptor(AddCookiesInterceptor())
            .addInterceptor(ReceivedCookiesInterceptor())
            .connectTimeout(NetworkConfig.ALL_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(NetworkConfig.ALL_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(NetworkConfig.ALL_TIMEOUT, TimeUnit.SECONDS)

        interceptor?.let {
            okHttpClient.addInterceptor(it)
        }

        return okHttpClient.build()
    }

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
    inner class HeaderSettingInterceptor : Interceptor {

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
    inner class AddCookiesInterceptor : Interceptor{
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val builder = chain.request().newBuilder()

            for(cookie in cookies){
                builder.addHeader("Cookie",cookie)
            }
            return chain.proceed(builder.build())
        }
    }

    inner class ReceivedCookiesInterceptor  : Interceptor{
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
}