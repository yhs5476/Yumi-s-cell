package com.example.carematch.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.carematch.data.model.CaregiverProfile
import com.example.carematch.data.model.CareRequest
import com.example.carematch.data.model.NotificationItem
import com.example.carematch.data.model.Review

@Database(
    entities = [
        CaregiverProfile::class,
        CareRequest::class,
        Review::class,
        NotificationItem::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun caregiverDao(): CaregiverDao
    abstract fun careRequestDao(): CareRequestDao
    abstract fun reviewDao(): ReviewDao
    abstract fun notificationDao(): NotificationDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "carematch_database.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
