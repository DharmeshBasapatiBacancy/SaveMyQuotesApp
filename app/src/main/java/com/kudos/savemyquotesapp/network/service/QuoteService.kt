package com.kudos.savemyquotesapp.network.service

import com.kudos.savemyquotesapp.network.model.RandomQuoteApi
import retrofit2.http.GET

interface QuoteService {

    @GET("random")
    suspend fun getRandomQuote(): RandomQuoteApi

}