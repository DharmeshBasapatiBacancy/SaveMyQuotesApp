package com.kudos.savemyquotesapp.di

import com.kudos.savemyquotesapp.db.dao.QuotesDao
import com.kudos.savemyquotesapp.network.service.QuoteService
import com.kudos.savemyquotesapp.repository.QuotesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideQuotesRepository(quoteService: QuoteService, quotesDao: QuotesDao): QuotesRepository {
        return QuotesRepository(quoteService,quotesDao)
    }

}