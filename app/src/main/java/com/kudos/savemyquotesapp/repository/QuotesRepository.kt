package com.kudos.savemyquotesapp.repository

import com.kudos.savemyquotesapp.db.dao.QuotesDao
import com.kudos.savemyquotesapp.db.entity.RandomQuoteDB
import com.kudos.savemyquotesapp.network.model.RandomQuoteApi
import com.kudos.savemyquotesapp.network.service.QuoteService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuotesRepository @Inject constructor(
    private val quoteService: QuoteService,
    private val quotesDao: QuotesDao
) {

    suspend fun getRandomQuoteFromApi(): RandomQuoteApi {
        return quoteService.getRandomQuote()
    }

    suspend fun insertRandomQuoteInDB(randomQuoteDB: RandomQuoteDB) {
        quotesDao.insertRandomQuote(randomQuoteDB)
    }

    fun getAllRandomQuotesFromDB(): Flow<List<RandomQuoteDB>> {
        return quotesDao.getAllRandomQuotes()
    }
}