package com.example.edinburghtransportation.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.edinburghtransportation.data.local.entity.CardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {

    @Query("SELECT * FROM cards ORDER BY isFavorite DESC, id DESC")
    fun getAllCards(): Flow<List<CardEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: CardEntity)


    @Query("UPDATE cards SET balance = balance + :amount WHERE id = :cardId")
    suspend fun updateBalance(cardId: Int, amount: Double)

    @Query("UPDATE cards SET isFavorite = :isFavorite WHERE id = :cardId")
    suspend fun updateFavorite(
        cardId: Int,
        isFavorite: Boolean
    )
    @Query("SELECT COUNT(*) FROM cards WHERE cardNumber = :cardNumber")
    suspend fun cardExists(cardNumber: String): Int
}