package com.example.principal.codechallenge.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.example.principal.codechallenge.AuthoredChallenge
import com.example.principal.codechallenge.UserDatabase
import com.example.principal.codechallenge.database.dao.AuthoredChallengeDao
import com.example.principal.codechallenge.database.dao.UserDao

@Database(entities = arrayOf(UserDatabase::class, AuthoredChallenge::class), version = 3, exportSchema = false )
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun authoredChallengesDao(): AuthoredChallengeDao
}