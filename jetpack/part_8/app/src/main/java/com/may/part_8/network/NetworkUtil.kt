package com.may.part_8.network

import com.may.part_8.GitHubApi
import com.may.part_8.GitHubApi2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
//@InstallIn(ActivityComponent::class)
object NetworkUtil {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    @GitHubApi
    fun teamApiService(): ApiService {
        return retrofit.create()
    }

    @Singleton
    @Provides
    @GitHubApi2
    fun repoApiService(): ApiService {
        val retrofit = Retrofit.Builder()
            //实际开发过程中, 可能baseurl并不只有一个，这里只做示范
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create()
    }
}