package com.kb.choco.injection

import android.content.Context
import com.kb.choco.data.db.AppDao
import com.kb.choco.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun getAppDb(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getAppDB(context)
    }

    @Provides
    @Singleton
    fun getAppDao(db: AppDatabase): AppDao {
        return db.getDao()
    }
}