package com.kudos.savemyquotesapp.di

import android.content.Context
import androidx.room.Room
import com.kudos.savemyquotesapp.db.QuotesDatabase
import com.kudos.savemyquotesapp.db.dao.QuotesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
private object DatabaseModule {

    @Provides
    fun provideQuotesDao(quotesDatabase: QuotesDatabase): QuotesDao {
        return quotesDatabase.quotesDao()
    }

    @Provides
    @Singleton
    fun provideQuotesDatabase(
        @ApplicationContext context: Context,
    ): QuotesDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            QuotesDatabase::class.java,
            "QuotesDB"
        ).build()
    }

}