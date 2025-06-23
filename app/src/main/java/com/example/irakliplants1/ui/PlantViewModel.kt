package com.example.irakliplants1.ui

import androidx.lifecycle.*
import com.example.irakliplants1.data.Plant
import com.example.irakliplants1.data.PlantRepository
import kotlinx.coroutines.launch

class PlantViewModel(private val repository: PlantRepository) : ViewModel() {

    val plants: LiveData<List<Plant>> = repository.getAllPlants().asLiveData()

    fun getPlantById(id: Int): LiveData<Plant?> = liveData {
        val plant = repository.getPlantById(id)
        emit(plant)
    }

    fun insert(plant: Plant) = viewModelScope.launch {
        repository.insertPlant(plant)
    }

    fun insertPlant(plant: Plant) = viewModelScope.launch {
        repository.insertPlant(plant)
    }

    fun delete(plant: Plant) = viewModelScope.launch {
        repository.deletePlant(plant)
    }
}
