package com.example.edinburghtransportation.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards")
data class CardEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val cardNumber: String,

    val balance: Double,

    val isFavorite: Boolean = false
)