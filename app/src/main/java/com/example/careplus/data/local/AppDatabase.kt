package com.example.careplus.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        CareRequestEntity::class,
        CaregiverProfileEntity::class,
        CareBidEntity::class,
        ChatMessageEntity::class,
        ContractEntity::class
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(CareConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun careDao(): CareDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "careplus_db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
