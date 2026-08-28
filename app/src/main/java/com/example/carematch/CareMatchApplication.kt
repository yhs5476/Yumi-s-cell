package com.example.carematch

import android.app.Application
import com.example.carematch.data.local.AppDatabase
import com.example.carematch.data.local.DatabaseInitializer
import com.example.carematch.data.repository.CareMatchRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class CareMatchApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val database: AppDatabase by lazy {
        AppDatabase.getDatabase(this)
    }

    val repository: CareMatchRepository by lazy {
        CareMatchRepository(database)
    }

    override fun onCreate() {
        super.onCreate()
        applicationScope.launch {
            DatabaseInitializer.seedDatabaseIfEmpty(database)
        }
    }
}
