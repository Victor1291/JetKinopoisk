package com.example.database.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
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
            "cinema")
            //.fallbackToDestructiveMigration().build()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    // Выполняем заполнение таблицы при создании базы данных

                    val insertFavirite="INSERT INTO collections VALUES(1,'Любимые',0,1,0)"
                    db.execSQL(insertFavirite)
                    val insertWant="INSERT INTO collections VALUES(2,'Хочу посмотреть',0,2,0)"
                    db.execSQL(insertWant)
                }
            }
            ).build()



    @Provides
    fun provideMovieDao(appDatabase: MovieDatabase): MovieDao {
        return appDatabase.getMovieDao()
    }

}
