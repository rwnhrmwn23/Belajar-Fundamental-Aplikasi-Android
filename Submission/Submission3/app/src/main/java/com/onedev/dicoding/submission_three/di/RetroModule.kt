package com.onedev.dicoding.submission_three.di

import com.onedev.dicoding.submission_three.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetroModule {
    companion object {
        private const val BASE_URL = "https://api.github.com/"

        private val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .method(original.method(), original.body())
                    .addHeader("Authorization", BuildConfig.KEY)
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()
    }

    @Singleton
    @Provides
    fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun getRetrofitServiceInstance(retrofit: Retrofit): RetroService {
        return retrofit.create(RetroService::class.java)
    }
}