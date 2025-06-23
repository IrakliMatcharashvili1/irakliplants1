package com.example.irakliplants1

import android.app.Application
import com.example.irakliplants1.data.AppDatabase
import com.example.irakliplants1.data.PlantRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class App : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { AppDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { PlantRepository(database.plantDao()) }

    companion object {
        lateinit var instance: App
            private set
        val repository get() = instance.repository
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
