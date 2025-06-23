package com.example.irakliplants1.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plants")
data class Plant(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val latinName: String,
    val naturalHabitat: String,
    val bloomingSeason: String?,
    val soil: String?,
    val careInstructions: String,
    val interestingFact: String?,
    val wikipediaUrl: String?,
    val photoUri: String?
)
