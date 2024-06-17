package com.example.database.di

import android.content.Context
import androidx.room.Room
import com.example.database.MovieDao
import com.example.database.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): MovieDatabase =
        Room.databaseBuilder(
            checkNotNull(context.applicationContext),
            MovieDatabase::class.java,
            "cinema"
        ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideMovieDao(appDatabase: MovieDatabase): MovieDao {
        return appDatabase.getMovieDao()
    }

}
