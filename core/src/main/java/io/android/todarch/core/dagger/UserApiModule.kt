/*
 * Copyright 2018 Todarch
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.android.todarch.core.dagger

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import io.android.todarch.core.BuildConfig
import io.android.todarch.core.data.api.UserService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * @author Melih Gültekin <mmelihgultekin@gmail.com>
 * @since 5.01.2019.
 */
@Module
class UserApiModule {

    @Singleton
    @Provides
    internal fun provideServices(@Named(NAMED_USER) retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Singleton
    @Provides
    @Named(NAMED_USER)
    internal fun provideRetrofit(@Named(NAMED_USER) okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    @Named(NAMED_USER)
    internal fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        @Named(NAMED_USER) interceptors: Array<Interceptor>?,
        stethoInterceptor: StethoInterceptor?
    ): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.connectTimeout(TIMEOUT_SECOND, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECOND, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECOND, TimeUnit.SECONDS)

        if (stethoInterceptor != null) {
            httpClientBuilder.addNetworkInterceptor(stethoInterceptor)
        }

        for (interceptor in interceptors.orEmpty()) {
            httpClientBuilder.addInterceptor(interceptor)
        }

        httpLoggingInterceptor.level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        httpClientBuilder.addInterceptor(httpLoggingInterceptor)

        return httpClientBuilder.build()
    }

    @Singleton
    @Provides
    @Named(NAMED_USER)
    internal fun provideHttpInterceptors(): Array<Interceptor>? = null

    companion object {
        const val TIMEOUT_SECOND: Long = 30
        const val NAMED_USER: String = "user"
    }
}