package com.example.edinburghtransportation.data.repository

import com.example.edinburghtransportation.data.local.dao.CardDao
import com.example.edinburghtransportation.data.local.entity.CardEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface CardRepository {

    fun getAllCards(): Flow<List<CardEntity>>

    suspend fun addCard(card: CardEntity)

    suspend fun cardExists(cardNumber: String): Boolean

    suspend fun updateBalance(
        cardId: Int,
        amount: Double
    )
    suspend fun updateFavorite(
        cardId: Int,
        isFavorite: Boolean
    )
}

class CardRepositoryImpl @Inject constructor(
    private val cardDao: CardDao
) : CardRepository {

    override fun getAllCards(): Flow<List<CardEntity>> {
        return cardDao.getAllCards()
    }

    override suspend fun addCard(card: CardEntity) {
        cardDao.insertCard(card)
    }

    override suspend fun updateBalance(
        cardId: Int,
        amount: Double
    ) {
        cardDao.updateBalance(cardId, amount)
    }

    override suspend fun updateFavorite(
        cardId: Int,
        isFavorite: Boolean
    ) {
        cardDao.updateFavorite(
            cardId,
            isFavorite
        )
    }

    override suspend fun cardExists(cardNumber: String): Boolean {
        return cardDao.cardExists(cardNumber) > 0
    }

}