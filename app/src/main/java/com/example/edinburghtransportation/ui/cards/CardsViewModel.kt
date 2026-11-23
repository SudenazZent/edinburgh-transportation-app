package com.example.edinburghtransportation.ui.cards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edinburghtransportation.data.local.entity.CardEntity
import com.example.edinburghtransportation.data.repository.CardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardsViewModel @Inject constructor(
    private val cardRepository: CardRepository
) : ViewModel() {

    val cards: StateFlow<List<CardEntity>> =
        cardRepository
            .getAllCards()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun addCard(
        cardNumber: String,
        balance: Double,
        isFavorite: Boolean = false,
        onResult: (Boolean) -> Unit = {}
    ) {
        viewModelScope.launch {

            if (cardRepository.cardExists(cardNumber)) {
                onResult(false)
                return@launch
            }

            val card = CardEntity(
                cardNumber = cardNumber,
                balance = balance,
                isFavorite = isFavorite
            )

            cardRepository.addCard(card)

            onResult(true)
        }
    }

    fun updateBalance(
        cardId: Int,
        amount: Double
    ) {
        viewModelScope.launch {
            cardRepository.updateBalance(cardId, amount)
        }
    }

    fun updateFavorite(
        cardId: Int,
        isFavorite: Boolean
    ) {
        viewModelScope.launch {
            cardRepository.updateFavorite(cardId, isFavorite)
        }
    }
}