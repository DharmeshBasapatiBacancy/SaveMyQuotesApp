package com.kudos.savemyquotesapp.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "randomQuote")
data class RandomQuoteDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val quoteAuthor: String,
    val quoteContent: String
):java.io.Serializable
