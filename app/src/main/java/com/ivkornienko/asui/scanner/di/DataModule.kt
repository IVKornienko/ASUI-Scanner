package com.ivkornienko.asui.scanner.di

import android.app.Application
import com.ivkornienko.asui.scanner.data.database.AppDatabase
import com.ivkornienko.asui.scanner.data.database.ProductInfoDao
import com.ivkornienko.asui.scanner.data.network.ApiInterceptor
import com.ivkornienko.asui.scanner.data.repository.ConnectionSettingsRepositoryImpl
import com.ivkornienko.asui.scanner.data.repository.ProductInfoRepositoryImpl
import com.ivkornienko.asui.scanner.data.repository.StorageConnectionSettingsRepositoryImpl
import com.ivkornienko.asui.scanner.domain.repository.ConnectionSettingsRepository
import com.ivkornienko.asui.scanner.domain.repository.ProductInfoRepository
import com.ivkornienko.asui.scanner.domain.repository.StorageConnectionSettingsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindConnectionSettingsRepository(impl: ConnectionSettingsRepositoryImpl): ConnectionSettingsRepository

    @Binds
    @ApplicationScope
    fun bindStorageConnectionSettingsRepository(impl: StorageConnectionSettingsRepositoryImpl): StorageConnectionSettingsRepository

    @Binds
    @ApplicationScope
    fun bindProductInfoRepository(impl: ProductInfoRepositoryImpl): ProductInfoRepository

    companion object {

        @Provides
        @ApplicationScope
        fun provideProductInfoDao(
            application: Application
        ): ProductInfoDao {
            return AppDatabase.getInstance(application).productInfoDao()
        }

        @Provides
        @ApplicationScope
        fun provideInterceptor(): ApiInterceptor {
            return ApiInterceptor()
        }

        @Provides
        @ApplicationScope
        fun provideOkHttpClient(interceptor: ApiInterceptor): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(interceptor)
                .build()
        }

        @Provides
        @ApplicationScope
        fun provideRetrofitBuilder(
            okHttpClient: OkHttpClient
        ): Retrofit.Builder {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .baseUrl("http://localhost/")
        }

    }

}