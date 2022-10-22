package com.ivkornienko.asui.scanner.di

import android.app.Application
import com.ivkornienko.asui.scanner.data.database.AppDatabase
import com.ivkornienko.asui.scanner.data.database.ProductInfoDao
import com.ivkornienko.asui.scanner.data.repository.ConnectionSettingsRepositoryImpl
import com.ivkornienko.asui.scanner.data.repository.ProductInfoRepositoryImpl
import com.ivkornienko.asui.scanner.data.repository.StorageConnectionSettingsRepositoryImpl
import com.ivkornienko.asui.scanner.domain.repository.ConnectionSettingsRepository
import com.ivkornienko.asui.scanner.domain.repository.ProductInfoRepository
import com.ivkornienko.asui.scanner.domain.repository.StorageConnectionSettingsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

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
    }

}