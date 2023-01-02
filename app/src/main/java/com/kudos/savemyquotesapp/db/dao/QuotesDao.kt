package com.kudos.savemyquotesapp.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kudos.savemyquotesapp.db.entity.RandomQuoteDB
import kotlinx.coroutines.flow.Flow

@Dao
interface QuotesDao {

    @Insert
    suspend fun insertRandomQuote(randomQuoteDB: RandomQuoteDB)

    @Query("SELECT * FROM randomQuote")
    fun getAllRandomQuotes(): Flow<List<RandomQuoteDB>>

}