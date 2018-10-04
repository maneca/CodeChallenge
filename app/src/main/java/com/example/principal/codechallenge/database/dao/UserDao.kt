package com.example.principal.codechallenge.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.principal.codechallenge.UserDatabase

@Dao
interface UserDao {

    @Query("SELECT * FROM user ORDER BY time asc LIMIT 5")
    fun getLastFive(): LiveData<List<UserDatabase>>


    @Query("SELECT COUNT(*) from user")
    fun getNumber(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserDatabase)
}