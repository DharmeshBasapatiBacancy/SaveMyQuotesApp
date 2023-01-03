package com.kudos.savemyquotesapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kudos.savemyquotesapp.db.entity.RandomQuoteDB
import com.kudos.savemyquotesapp.repository.QuotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(quotesRepository: QuotesRepository) :
    ViewModel() {

    val randomQuotesList: LiveData<List<RandomQuoteDB>> =
        quotesRepository.getAllRandomQuotesFromDB().asLiveData()

}