package com.kudos.savemyquotesapp.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.kudos.savemyquotesapp.db.entity.RandomQuoteDB
import com.kudos.savemyquotesapp.repository.QuotesRepository
import com.kudos.savemyquotesapp.utils.makeStatusNotification
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class RandomQuoteFetchingWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val quotesRepository: QuotesRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {

            val inputValue = inputData.getString("myKey")
            Log.d("TAG", "IN WORKER - $inputValue")

            makeStatusNotification(
                "Random Quotes Fetcher",
                "Fetching random quote...",
                applicationContext
            )
            val apiResponse = quotesRepository.getRandomQuoteFromApi()
            val randomQuoteDB =
                RandomQuoteDB(quoteAuthor = apiResponse.author, quoteContent = apiResponse.content)
            quotesRepository.insertRandomQuoteInDB(randomQuoteDB)
            makeStatusNotification(
                "Random Quotes Fetcher",
                "Random quote fetched.",
                applicationContext
            )

            Result.success(workDataOf("workerKey" to "workerResult"))
        } catch (e: Exception) {
            Result.failure(workDataOf("workerKey" to e.localizedMessage))
        }
    }
}