package com.example.edinburghtransportation.di

import com.example.edinburghtransportation.data.repository.CardRepository
import com.example.edinburghtransportation.data.repository.CardRepositoryImpl
import com.example.edinburghtransportation.data.repository.TransitRepository
import com.example.edinburghtransportation.data.repository.TransitRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindTransitRepository(
        impl: TransitRepositoryImpl
    ): TransitRepository

    @Binds
    abstract fun bindCardRepository(
        impl: CardRepositoryImpl
    ): CardRepository
}