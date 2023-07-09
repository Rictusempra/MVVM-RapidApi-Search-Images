package com.foodsoul.test.di.module

import com.foodsoul.test.network.image_search.ImageSearchApi
import com.foodsoul.test.network.RetrofitFactory
import com.foodsoul.test.network.image_search.ApiConstants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
class RemoteModule() {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitFactory(okHttpClient: OkHttpClient): RetrofitFactory {
        return RetrofitFactory(okHttpClient)
    }

    @Provides
    @Singleton
    fun provideImageSearchApi(retrofitFactory: RetrofitFactory): ImageSearchApi {
        return retrofitFactory.getRetrofit(ApiConstants.BASE_URL).create(ImageSearchApi::class.java)
    }

}
