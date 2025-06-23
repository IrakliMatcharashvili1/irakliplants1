package com.example.irakliplants1.data

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader

class PlantDatabaseCallback(
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
        val jsonStream = context.assets.open("plants.json")
        val plantListType = object : TypeToken<List<Plant>>() {}.type
        val plants: List<Plant> = Gson().fromJson(InputStreamReader(jsonStream), plantListType)

        plantDao.insertAll(plants)
    }

    private val INSTANCE: AppDatabase?
        get() = AppDatabaseHolder.INSTANCE

    private object AppDatabaseHolder {
        var INSTANCE: AppDatabase? = null
    }
}
