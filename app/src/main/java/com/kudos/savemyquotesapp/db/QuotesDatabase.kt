package com.kudos.savemyquotesapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kudos.savemyquotesapp.db.dao.QuotesDao
import com.kudos.savemyquotesapp.db.entity.RandomQuoteDB

@Database(entities = [RandomQuoteDB::class], version = 1, exportSchema = false)
abstract class QuotesDatabase : RoomDatabase() {

    abstract fun quotesDao(): QuotesDao

}