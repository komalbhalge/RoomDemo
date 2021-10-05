package com.kb.choco.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kb.choco.data.db.entities.OrderEntity
import com.kb.choco.data.db.entities.ProductEntity
import com.kb.choco.data.db.relations.OrderProductCrosRef
import com.kb.choco.util.Converters
import com.kb.choco.util.DB_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [ProductEntity::class, OrderEntity::class, OrderProductCrosRef::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDao(): AppDao

    companion object {
        private var dbINSTANCE: AppDatabase? = null

        fun getAppDB(context: Context): AppDatabase {
            if (dbINSTANCE == null) {
                dbINSTANCE = Room.databaseBuilder<AppDatabase>(
                    context.applicationContext, AppDatabase::class.java, DB_NAME
                ).allowMainThreadQueries()
                    .build()
            }
            return dbINSTANCE!!
        }
    }
}