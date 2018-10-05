package com.example.principal.codechallenge.dependencyInjection

import android.app.Application
import android.arch.persistence.room.Room
import com.example.principal.codechallenge.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(app: Application) = Room.databaseBuilder(app, AppDatabase::class.java, "codechallenge.db")
            //.allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

    @Singleton @Provides
    fun provideUserDao(appDatabase: AppDatabase) = appDatabase.userDao()

    @Singleton @Provides
    fun provideAuthoredChallengesDao(appDatabase: AppDatabase) = appDatabase.authoredChallengesDao()


}