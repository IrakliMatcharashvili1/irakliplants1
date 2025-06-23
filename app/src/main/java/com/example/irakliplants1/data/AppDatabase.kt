package com.example.irakliplants1.data

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.InputStreamReader

@Database(entities = [Plant::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun plantDao(): PlantDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "plant_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(PlantDatabaseCallback(context, scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class PlantDatabaseCallback(
        private val context: Context,
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.plantDao())
                }
            }
        }

        private suspend fun populateDatabase(plantDao: PlantDao) {
            try {
                val inputStream = context.assets.open("plants.json")
                val reader = InputStreamReader(inputStream)
                val plantType = object : TypeToken<List<Plant>>() {}.type
                val plants: List<Plant> = Gson().fromJson(reader, plantType)
                reader.close()

                plantDao.insertAll(plants)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
