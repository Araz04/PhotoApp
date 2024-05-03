package com.example.infiniteimagesapp.di

import com.example.infiniteimagesapp.data.remote.PhotoAlbumsApiService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val baseUrl = "https://jsonplaceholder.typicode.com"

val createNetworkModule = module {
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single { provideOkHttpClient(get()) }
    single { providePhotoApi(get()) }
}

private fun providePhotoApi(okHttpClient: OkHttpClient): PhotoAlbumsApiService {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(okHttpClient)
        .build()
        .create(PhotoAlbumsApiService::class.java)
}

private fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient()
        .newBuilder()
        .addNetworkInterceptor(loggingInterceptor)
        .build()
}