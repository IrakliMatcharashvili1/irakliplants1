package com.example.irakliplants1.data

import kotlinx.coroutines.flow.Flow

class PlantRepository(private val plantDao: PlantDao) {
    fun getAllPlants(): Flow<List<Plant>> = plantDao.getAllPlants()
    suspend fun getPlantById(id: Int) = plantDao.getPlantById(id)
    suspend fun insertPlant(plant: Plant) = plantDao.insertPlant(plant)
    suspend fun deletePlant(plant: Plant) = plantDao.deletePlant(plant)
}
