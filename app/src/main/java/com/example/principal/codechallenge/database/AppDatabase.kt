package com.example.principal.codechallenge.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.principal.codechallenge.UserDatabase
import com.example.principal.codechallenge.database.dao.UserDao

@Database(entities = arrayOf(UserDatabase::class), version = 2, exportSchema = false )
abstract class AppDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

}